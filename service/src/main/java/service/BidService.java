package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Pojo.Hotel;
import Pojo.HotelBidRequest;
import Pojo.Order;

/**
 * Created by Administrator on 2015-3-21.
 */
public class BidService {

	private static Logger log = LoggerFactory.getLogger(BidService.class);
	
	private static final BidService instance = new BidService();

	public static BidService getInstance() {
		return instance;
	}

	private static Map<Integer, Order> orderMap = new HashMap<>();

	static AtomicInteger id = new AtomicInteger(1);

	private BidService() {
		startBidScanTask();
	}

	private void startBidScanTask() {
		new Thread() {
			public void run() {
				while (true) {
					for (Map.Entry<Integer, Order> entry : orderMap.entrySet()) {
						Order order = entry.getValue();
						if (order.isTimeout()) {
							if (!order.isDead()) {
								if (order.shouldDead()) {
									log.info(String.format("%s is dead", order));
									order.setDead(true);
								} else {
									findWinningBid(order);
									if (order.getWinningBid() == null) {
										findBestLosingBid(order);
									}
								}
							}
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}

			private void findBestLosingBid(Order order) {
				if (order.getBestLosingBid() == null) {
					Random rnd = new Random(System.currentTimeMillis());
					for (Map.Entry<Integer, List<HotelBidRequest>> bidEntry : order.getBidMap().entrySet()) {
						List<HotelBidRequest> bidReqs = bidEntry.getValue();
						// TODO choose a random bid currently
						HotelBidRequest bestLosingBid = bidReqs.get(rnd.nextInt(bidReqs.size()));
						log.info(String.format("Choose %s as best losing bid for %s", bestLosingBid, order));
						order.setBestLosingBid(bestLosingBid);
					}
				}
			}

			private void findWinningBid(Order order) {
				if (order.getWinningBid() == null) {
					for (Map.Entry<Integer, List<HotelBidRequest>> bidEntry : order.getBidMap().entrySet()) {
						for (HotelBidRequest bid : bidEntry.getValue()) {
							if (bid.satify(order)) {
								order.setWinningBid(bid);
								log.info(String.format("Choose %s as winning bid for %s", bid, order));
								return;
							}
						}
					}
				}
			}
		}.start();
	}

	public int userBid(Order order) {
		int generatedId = generateId();
		order.setOrderid(generatedId);
		orderMap.put(generatedId, order);
		return generatedId;
	}

	public void hotelBid(HotelBidRequest request, Order order) {
		order.addHotelBidRequest(request);
	}

	private int generateId() {
		return id.incrementAndGet();
	}

	public Order getDoneOrder(int orderId) {
		// todo: get done order by id.
		return null;
	}

	public List<Order> getOrderList(int hotelId) {
		List<Order> result = new ArrayList<Order>();
		HotelService hotelService = HotelService.getInstance();
		Hotel hotel = hotelService.getHotelById(hotelId);
		for (Order order : orderMap.values()) {
			if (order.getHotelRequest().getLocation().equals(hotel.getLocation())
			      && order.getHotelRequest().getType().equals(hotel.getType())) {
				result.add(order);
			}
		}
		return result;
	}

	public Order getOrder(int orderId) {
		return orderMap.get(orderId);
	}
}

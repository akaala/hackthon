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
import Pojo.UserBidRequest;
import Pojo.Order;
import Pojo.Order.OrderStatus;

/**
 * Created by Administrator on 2015-3-21.
 */
public class BidService {

	private static Logger log = LoggerFactory.getLogger(BidService.class);

	private static final BidService instance = new BidService();

	public static BidService getInstance() {
		return instance;
	}

	private Map<Integer, Order> orderMap = new HashMap<>();

	private AtomicInteger bidId = new AtomicInteger(0);

	private AtomicInteger hotelBidId = new AtomicInteger(0);

	private BidService() {
		startBidScanTask();
	}

	private void startBidScanTask() {
		new Thread() {
			public void run() {
				while (true) {
					for (Map.Entry<Integer, Order> entry : orderMap.entrySet()) {
						Order order = entry.getValue();
						if (order.getStatus() == OrderStatus.inbid) {
							findWinningBid(order);
							if (order.getWinningBid() != null) {
								order.setStatus(OrderStatus.done);
								continue;
							}
							if (order.isBidTimeout()) {
								order.setStatus(OrderStatus.extrabid);
								findBestLosingBid(order);
								continue;
							}
						} else if (order.getStatus() == OrderStatus.extrabid) {
							if (order.isExtraBidTimeout()) {
								log.info(String.format("%s is dead", order));
								order.setStatus(OrderStatus.done);
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

	public Order userBid(Order order) {
		int generatedId = bidId.incrementAndGet();
		order.setOrderid(generatedId);
		order.setStatus(OrderStatus.inbid);
		orderMap.put(generatedId, order);
		return order;
	}

	public HotelBidRequest hotelBid(HotelBidRequest request, Order order) {
		request.setBidId(hotelBidId.incrementAndGet());
		order.addHotelBidRequest(request);
		return request;
	}

	public List<Order> getMatchedOrders(int hotelId) {
		List<Order> result = new ArrayList<Order>();
		HotelService hotelService = HotelService.getInstance();
		Hotel hotel = hotelService.getHotelById(hotelId);
		for (Order order : orderMap.values()) {
			UserBidRequest hotelRequest = order.getHotelRequest();
			boolean isMatchLocation = hotelRequest.getLocation() != null ? true : false;
			if (!isMatchLocation) {
				isMatchLocation = hotelRequest.getLocation().equals(hotel.getLocation());
			}
			boolean isMatchType = hotelRequest.getType() != null ? true : false;
			if (!isMatchType) {
				isMatchType = hotelRequest.getType().equals(hotel.getType());
			}
			boolean isMatchStar = hotelRequest.getStar() != null ? true : false;
			if (!isMatchStar) {
				isMatchStar = hotelRequest.getStar().equals(hotel.getStar());
			}
			if (isMatchLocation && isMatchType && isMatchStar) {
				result.add(order);
			}
		}
		return result;
	}

	public Order getOrder(int orderId) {
		return orderMap.get(orderId);
	}

	public Order confirmOrderBid(int orderId, int hotelBidId) {
		Order order = getOrder(orderId);
		HotelBidRequest hotelBidRequest = order.getHotelBidRequest(hotelBidId);
		order.setWinningBid(hotelBidRequest);
		return order;
	}
}

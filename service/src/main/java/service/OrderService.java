package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import Pojo.Hotel;
import Pojo.Order;

/**
 * Created by Administrator on 2015-3-21.
 */
public class OrderService {

	private static final OrderService instance = new OrderService();

	public static OrderService getInstance() {
		return instance;
	}

	private static Map<Integer, Order> orderMap = new HashMap<>();

	static AtomicInteger id = new AtomicInteger(1);

	private OrderService() {

	}

	public int userBid(Order order) {
		int generatedId = generateId();
		order.setOrderid(generatedId);
		orderMap.put(generatedId, order);
		return generatedId;
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
}

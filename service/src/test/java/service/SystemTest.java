package service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import Pojo.Hotel;
import Pojo.HotelBidRequest;
import Pojo.HotelRequest;
import Pojo.Order;
import Pojo.User;

public class SystemTest {

	@Test
	public void testSimpleBid() throws Exception {
		UserService userService = UserService.getInstance();

		int myUserId = 1;
		User user = userService.getUserById(myUserId);

		Order order = new Order();
		order.setCreateTime(new Date());
		order.setExpiretime(5);
		order.setUser(user);
		HotelRequest hotelRequest = new HotelRequest();
		hotelRequest.setLocation("上海-徐家汇");
		hotelRequest.setStar(3);
		hotelRequest.setType("经济型");
		order.setHotelRequest(hotelRequest);

		BidService orderService = BidService.getInstance();
		orderService.userBid(order);

		HotelService hotelService = HotelService.getInstance();
		int myHotelId = 1;
		Hotel hotel = hotelService.getHotelById(myHotelId);
		List<Order> orders = orderService.getOrderList(hotel.getHotelid());
		System.out.println(orders);

		if (orders.size() > 0) {
			HotelBidRequest request = new HotelBidRequest();
			request.setComment("我要我要我要");
			request.setCreateDate(new Date());
			request.setExtraPrice(5);
			request.setHotelId(hotel.getHotelid());
			orderService.hotelBid(request, orders.get(0));
		}

		Order latestOrder = orderService.getOrder(order.getOrderid());
		System.out.println(latestOrder);
	}
}

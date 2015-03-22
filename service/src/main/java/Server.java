import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import service.BidService;
import service.HotelService;
import service.PriceService;
import service.UserService;
import spark.Response;
import Pojo.Hotel;
import Pojo.HotelBidRequest;
import Pojo.Order;
import Pojo.Order.OrderStatus;
import Pojo.PricePoint;
import Pojo.User;
import Pojo.UserBidRequest;

import com.alibaba.fastjson.JSON;

public class Server {
	public static void main(String[] args) {
		spark.Spark.staticFileLocation("/web");

		BidService orderService = BidService.getInstance();
		UserService userService = UserService.getInstance();
		HotelService hotelService = HotelService.getInstance();

		PriceService service = PriceService.getInstance();

		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: OrderID or null(means failed)
		 */
		spark.Spark.get("/order/userbid", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userid"));
			int price = Integer.valueOf(req.queryParams("price"));
			int star = Integer.valueOf(req.queryParams("star"));
			String place = req.queryParams("place");
			String type = req.queryParams("type");
			int timeout = Integer.valueOf(req.queryParams("timeout"));

			Order order = new Order();
			order.setUser(userService.getUserById(userId));
			UserBidRequest request = new UserBidRequest();
			request.setPrice(price);
			request.setStar(star);
			request.setType(type);
			request.setLocation(place);
			order.setExpiretime(timeout);
			order.setHotelRequest(request);
			order.setCreateTime(new Date());

			// put into userToOrderHistory
			   userService.addUserOrder(userId, order);

			   order = orderService.userBid(order);

			   addHeader(res);
			   return JSON.toJSON(order);
		   });

		spark.Spark.get("/order/hotelbid", (req, res) -> {
			int hotelId = Integer.valueOf(req.queryParams("hotelid"));
			int orderId = Integer.valueOf(req.queryParams("orderid"));
			int extra = Integer.valueOf(req.queryParams("extra"));
			String comment = req.queryParams("comment");

			Hotel hotel = hotelService.getHotelById(hotelId);

			HotelBidRequest request = new HotelBidRequest();
			request.setComment(comment);
			request.setExtraPrice(extra);
			request.setCreateDate(new Date());
			request.setHotel(hotel);
			Order order = orderService.getOrder(orderId);
			HotelBidRequest hotelBid = orderService.hotelBid(request, order);
			addHeader(res);
			return JSON.toJSON(hotelBid);
		});

		/**
		 * Input: orderid Output: Order
		 */
		spark.Spark.get("/order/confirm", (req, res) -> {
			int orderId = Integer.valueOf(req.queryParams("orderid"));
			int hotelBidId = Integer.valueOf(req.queryParams("hotelbidid"));
			Order order = orderService.confirmOrderBid(orderId, hotelBidId);
			addHeader(res);
			return JSON.toJSON(order);
		});

		spark.Spark.get("/order/counts", (req, res) -> {
			Map<OrderStatus, Integer> orderCounts = orderService.getOrderCounts();
			addHeader(res);
			return JSON.toJSON(orderCounts);
		});

		// show bid history and 概率
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: Map<Price, Probability>
		 */
		spark.Spark.get("/order/probability", (req, res) -> {
			UserBidRequest request = new UserBidRequest();
			request.setType(req.queryParams("type"));
			request.setStar(Integer.valueOf(req.queryParams("star")));
			request.setLocation(req.queryParams("place"));

			int timeoutMin = Integer.valueOf(req.queryParams("timeout"));

			double normalPrice = service.getNormalPrice(request);
			List<PricePoint> prices = service.getPricePoint(normalPrice, timeoutMin, new Date());
			addHeader(res);
			return JSON.toJSON(prices);
		});

		/**
		 * Input: UserID Output: List<Order>
		 */
		spark.Spark.get("/user/orders", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userid"));
			List<Order> orders = userService.getUserOrders(userId);
			addHeader(res);

			return JSON.toJSON(orders);
		});

		/**
		 * Input: hotelid Output: List<Order>
		 */
		// hotel to check all his bids.
		spark.Spark.get("/hotel/orders", (req, res) -> {
			int hotelId = Integer.valueOf(req.queryParams("hotelid"));

			List<Order> orders = orderService.getMatchedOrders(hotelId);
			addHeader(res);
			return JSON.toJSON(orders);
		});

		/**
		 * Input: Orderid Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
		 */
		spark.Spark.get("/order/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			Order order = orderService.getOrder(orderId);
			addHeader(res);
			return JSON.toJSON(order);
		});

		spark.Spark.get("/order/list", (req, res) -> {
			Collection<Order> orders = orderService.getOrders();
			addHeader(res);
			return JSON.toJSON(orders);
		});

		spark.Spark.get("/user/:userid", (req, res) -> {
			int userId = Integer.valueOf(req.params(":userid"));
			User user = userService.getUserById(userId);
			addHeader(res);
			return JSON.toJSON(user);
		});

		spark.Spark.get("/user/list", (req, res) -> {
			Collection<User> users = userService.getUsers();
			addHeader(res);
			return JSON.toJSON(users);
		});

		spark.Spark.get("/hotel/:hotelid", (req, res) -> {
			int hotelId = Integer.valueOf(req.params(":hotelid"));
			Hotel hotel = hotelService.getHotelById(hotelId);
			addHeader(res);
			return JSON.toJSON(hotel);
		});

		spark.Spark.get("/hotel/list", (req, res) -> {
			Collection<Hotel> hotels = hotelService.getHotels();
			addHeader(res);
			return JSON.toJSON(hotels);
		});

	}

	private static void addHeader(Response response) {
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		response.header("Access-Control-Allow-Headers", "Content-Type");
	}
}

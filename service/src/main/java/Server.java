import Pojo.Order;

import com.alibaba.fastjson.JSON;

import service.HotelService;
import service.BidService;
import service.PriceService;
import service.UserService;

import java.util.Date;
import java.util.List;

import service.BidService;
import service.HotelService;
import service.PriceService;
import service.UserService;
import Pojo.Hotel;
import Pojo.HotelBidRequest;
import Pojo.Order;
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
			return JSON.toJSON(hotelBid);
		});

		/**
		 * Input: orderid Output: Order
		 */
		spark.Spark.get("/order/confirm", (req, res) -> {
			int orderId = Integer.valueOf(req.queryParams("orderid"));
			int hotelBidId = Integer.valueOf(req.queryParams("hotelbidid"));
			Order order = orderService.confirmOrderBid(orderId, hotelBidId);
			return JSON.toJSON(order);
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
			// request.setPrice(Integer.valueOf(req.queryParams("price")));

			   int timeoutMin = Integer.valueOf(req.queryParams("timeout"));

			   double normalPrice = service.getNormalPrice(request);
			   List<PricePoint> prices = service.getPricePoint(normalPrice, timeoutMin, new Date());
			   return JSON.toJSON(prices);
		   });

		/**
		 * Input: UserID Output: List<Order>
		 */
		spark.Spark.get("/user/orders", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userid"));
			List<Order> orders = userService.getUserOrders(userId);
			return JSON.toJSON(orders);
		});

		/**
		 * Input: hotelid Output: List<Order>
		 */
		// hotel to check all his bids.
		spark.Spark.get("/hotel/orders", (req, res) -> {
			int hotelId = Integer.valueOf(req.queryParams("hotelid"));

			List<Order> orders = orderService.getMatchedOrders(hotelId);
			return JSON.toJSON(orders);
		});

		/**
		 * Input: Orderid Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
		 */
		spark.Spark.get("/order/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			Order order = orderService.getOrder(orderId);
			return JSON.toJSON(order);
		});

		spark.Spark.get("/user/:userid", (req, res) -> {
			int userId = Integer.valueOf(req.params(":userid"));
			User user = userService.getUserById(userId);
			return JSON.toJSON(user);
		});

		spark.Spark.get("/hotel/:hotelid", (req, res) -> {
			int hotelId = Integer.valueOf(req.params(":hotelid"));
			Hotel hotel = hotelService.getHotelById(hotelId);
			return JSON.toJSON(hotel);
		});

	}
}

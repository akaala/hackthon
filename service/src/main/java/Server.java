import Pojo.Order;
import Pojo.HotelRequest;

import com.google.common.collect.ArrayListMultimap;

import service.HotelService;
import service.BidService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;


public class Server {
    public static void main(String[] args) {
        spark.Spark.staticFileLocation("/web");

		BidService orderService = BidService.getInstance();
		UserService userService = UserService.getInstance();
		HotelService hotelService = HotelService.getInstance();

		ArrayListMultimap<Integer /* userid */, Order> userToOrderHistory = ArrayListMultimap.create();

		Map<Integer /* orderid */, Order> idToOrderList = new HashMap<>();

		// custom do bid.
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: OrderID or null(means failed)
		 */
        spark.Spark.get("/order/buy", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userId"));
			int price = Integer.valueOf(req.queryParams("price"));
			int star = Integer.valueOf(req.queryParams("start"));
			String place = req.queryParams("place");
			String type = req.queryParams("type");

			Order order = new Order();
			order.setUser(userService.getUserById(userId));
			HotelRequest request = new HotelRequest();
			request.setPrice(price);
			request.setStar(star);
			request.setType(type);
			request.setLocation(place);
			order.setHotelRequest(request);

			// put into userToOrderHistory
			   userToOrderHistory.put(userId, order);

			   return orderService.userBid(order);
		   });

		// show bid history and 概率
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: Map<Price, Probability>
		 */
        spark.Spark.get("/order/probability", (req, res) -> {
			String price = req.queryParams("price");
			// Todo: TBD
			   return 0;
		   });

		/**
		 * Input: UserID Output: List<Order>
		 */
        spark.Spark.get("/order/:userid/history", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userId"));
			return userToOrderHistory.get(userId);
		});

		/**
		 * Input: Orderid Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
		 */
        spark.Spark.get("/bid/detail/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			// TBD
			   return idToOrderList.get(orderId);
		   });

		/**
		 * Input: hotelid Output: List<Order>
		 */
		// hotel to check all his bids.
        spark.Spark.get("/hotel/:hotelid/orderlist", (req, res) -> {
			int hotelId = Integer.valueOf(req.params(":hotelid"));

			return orderService.getOrderList(hotelId);
		});

		/**
		 * Input: orderid Output: Order
		 */
        spark.Spark.get("/bid/done/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			int hotelBidId = Integer.valueOf(req.params(":hotelBidId"));
			return orderService.confirmOrderBid(orderId, hotelBidId);
		});
	}
}

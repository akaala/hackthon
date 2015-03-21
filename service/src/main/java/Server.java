import Pojo.Hotel;
import Pojo.Order;
import Pojo.OrderRequest;

import com.alibaba.fastjson.JSON;

import Pojo.User;

import com.google.common.collect.ArrayListMultimap;

import service.HotelService;
import service.OrderService;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Server {
    public static void main(String[] args) {
    	staticFileLocation("/web");

		OrderService orderService = new OrderService();
		UserService userService = new UserService();
		HotelService hotelService = new HotelService();

		ArrayListMultimap<Integer /* userid */, Order> userToOrderHistory = ArrayListMultimap.create();

		Map<Integer /* orderid */, Order> idToOrderList = new HashMap<>();

		// custom do bid.
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: OrderID or null(means failed)
		 */
		get("/order/buy", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userId"));
			int price = Integer.valueOf(req.queryParams("price"));
			int star = Integer.valueOf(req.queryParams("start"));
			String place = req.queryParams("place");
			String type = req.queryParams("type");

			Order order = new Order();
			order.setUser(userService.getUserById(userId));
			OrderRequest request = new OrderRequest();
			request.setPrice(price);
			request.setStar(star);
			request.setType(type);
			request.setPlace(place);
			order.setOrderRequest(request);

			// put into userToOrderHistory
			   userToOrderHistory.put(userId, order);

			   return orderService.buy(order);
		   });

		// show bid history and 概率
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: Map<Price, Probability>
		 */
		get("/order/probability", (req, res) -> {
			String price = req.queryParams("price");
			// Todo: TBD
			   return 0;
		   });

		/**
		 * Input: UserID Output: List<Order>
		 */
		get("/order/:userid/history", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userId"));
			return userToOrderHistory.get(userId);
		});

		/**
		 * Input: Orderid Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
		 */
		get("/bid/detail/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			// TBD
			   return idToOrderList.get(orderId);
		   });

		/**
		 * Input: hotelid Output: List<Order>
		 */
		// hotel to check all his bids.
		get("/hotel/:hotelid/orderlist", (req, res) -> {
			int hotelId = Integer.valueOf(req.params(":hotelid"));

			return hotelService.getOrderList(hotelId);
		});

		/**
		 * Input: orderid Output: Order
		 */
		get("/bid/done/:orderid", (req, res) -> {
			int orderId = Integer.valueOf(req.params(":orderid"));
			return orderService.getDoneOrder(orderId);
		});
	}
}

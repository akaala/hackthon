import Pojo.Order;
import Pojo.HotelRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;

import service.HotelService;
import service.BidService;
import service.PriceService;
import service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Server {
    public static void main(String[] args) {
        spark.Spark.staticFileLocation("/web");

		BidService orderService = BidService.getInstance();
		UserService userService = UserService.getInstance();
		HotelService hotelService = HotelService.getInstance();

	    PriceService service = PriceService.getInstance();

		// custom do bid.
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: OrderID or null(means failed)
		 */
        spark.Spark.get("/order/buy", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userid"));
			int price = Integer.valueOf(req.queryParams("price"));
			int star = Integer.valueOf(req.queryParams("star"));
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
	        order.setCreateTime(new Date());

			// put into userToOrderHistory
	         userService.addUserOrder(userId, order);

			   return orderService.userBid(order);
		   });

		// show bid history and 概率
		/**
		 * Input: UserID, Price, Star, Place, Type, ExpireTime Output: Map<Price, Probability>
		 */
        spark.Spark.get("/order/probability", (req, res) -> {
	        HotelRequest request = new HotelRequest();
	        request.setType(req.queryParams("type"));
	        request.setStar(Integer.valueOf(req.queryParams("star")));
	        request.setLocation(req.queryParams("place"));
//	        request.setPrice(Integer.valueOf(req.queryParams("price")));

	        int timeoutMin = Integer.valueOf(req.queryParams("timeout"));

	        double normalPrice = service.getNormalPrice(request);
	        return JSON.toJSON(service.getPricePoint(normalPrice, timeoutMin , new Date()));
		   });

		/**
		 * Input: UserID Output: List<Order>
		 */
        spark.Spark.get("/order/history", (req, res) -> {
			int userId = Integer.valueOf(req.queryParams("userid"));
			return userService.getUserOrders(userId);
		});

		/**
		 * Input: Orderid Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
		 */
        spark.Spark.get("/order/detail", (req, res) -> {
			int orderId = Integer.valueOf(req.queryParams("orderid"));
			   return orderService.getOrder(orderId);
		   });

		/**
		 * Input: hotelid Output: List<Order>
		 */
		// hotel to check all his bids.
        spark.Spark.get("/hotel/orderlist", (req, res) -> {
			int hotelId = Integer.valueOf(req.queryParams("hotelid"));

	         return orderService.getOrderList(hotelId);
		});

	    spark.Spark.get("/hotel/get", (req, res) -> {
		    int hotelId = Integer.valueOf(req.queryParams("hotelid"));
		    int orderId = Integer.valueOf(req.queryParams("orderid"));
				//todo
		    return null;
	    });

	/*	*//**
		 * Input: orderid Output: Order
		 *//*
        spark.Spark.get("/hotel/add", (req, res) -> {
			int orderId = Integer.valueOf(req.params("orderid"));
			return orderService.getDoneOrder(orderId);
		});*/
	}
}

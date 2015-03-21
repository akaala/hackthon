import Pojo.Hotel;
import Pojo.Order;
import com.alibaba.fastjson.JSON;

import Pojo.User;
import com.google.common.collect.ArrayListMultimap;
import service.OrderService;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        UserService userService = new UserService();
        User user = new User();

        String userS = JSON.toJSONString(user);


        ArrayListMultimap<Integer /*userid*/, Order> userToOrderHistory
                = ArrayListMultimap.create();

        Map<Integer /*orderid*/, Order> idToOrderList = new HashMap<>();


/*		get("/hello/:id", (req, res) -> {
            int id = Integer.valueOf(req.params(":id"));
			return ++id;
		});*/

        // custom do bid.
        /**
         * Input: UserID, Price, Star, Place, Type, ExpireTime
         * Output: OrderID or null(means failed)
         */
        get("/order/buy", (req, res) -> {
                    int userId = Integer.valueOf(req.queryParams("userId"));
                    int price = Integer.valueOf(req.queryParams("price"));
                    int star = Integer.valueOf(req.queryParams("start"));
                    String place = req.queryParams("place");
                    String type = req.queryParams("type");

                    Order order = new Order();
                    order.setUser(userService.getUserById(userId));
                    order.setPrice(price);
                    order.setStar(star);
                    order.setType(type);
                    order.setPlace(place);

                    // put into userToOrderHistory
                    userToOrderHistory.put(userId, order);

                    return orderService.buy(order);
                }
        );

        // show bid history and 概率
        /**
         * Input: UserID, Price, Star, Place, Type, ExpireTime
         * Output: Map<Price, Probability>
         */
        get("/order/probability", (req, res) -> {
            String price = req.queryParams("price");
            return 0;
        });

        /**
         * Input: UserID
         * Output: List<Order>
         */
        get("/order/:userid/history", (req, res) -> {
            int userId = Integer.valueOf(req.queryParams("userId"));
            return userToOrderHistory.get(userId);
        });

        /**
         * Input: Orderid
         * Output: Order ： 主要用于竞拍页面，有哪些酒店浏览过，竞拍过。
         */
        get("/bid/detail/:orderid", (req, res) -> {
            int orderId = Integer.valueOf(req.params(":orderid"));
            // TBD
            return idToOrderList.get(orderId);
        });

        /**
         * Input: hotelid
         * Output: List<Order>
         */
        // hotel to check all his bids.
        get("/hotel/:id/orderlist", (req, res) -> {
            return new ArrayList<>();
        });


        /**
         * Input: orderid
         * Output: Order
         */
        get("/bid/done/:orderid", (req, res) -> {
            return "bid id and hotel info...";
        });
    }
}

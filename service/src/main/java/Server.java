import Pojo.Hotel;
import Pojo.Order;
import com.alibaba.fastjson.JSON;

import Pojo.User;
import service.OrderService;

import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        User user = new User();

        String userS = JSON.toJSONString(user);

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
//                    order.setUser(userService.getUser(userId));
                    order.setPrice(price);
                    order.setStar(star);
                    order.setType(type);
//                    order.setPlace();



return                      orderService.buy(order);
            }
        );

        // show bid history and 概率
        /**
         * Input: UserID, Price, Star, Place, Type, ExpireTime
         * Output: Map<Price, Probability>
         */
        get("/order/probability", (req, res) -> {
            String price = req.queryParams("price");
            return price;
        });

        /**
         * Input: UserID
         * Output: List<Order>
         */
        get("/order/:userid/history", (req, res) -> {
            return "all user bid history";
        });

        /**
         * Input: Orderid
         * Output: List<Order>
         */
        get("/bid/detail/:orderid", (req, res) -> {
            int orderId = Integer.valueOf(req.params(":orderid"));
            return orderId;
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

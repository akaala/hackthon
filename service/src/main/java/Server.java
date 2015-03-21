import com.alibaba.fastjson.JSON;

import Pojo.User;

import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {
    public static void main(String[] args) {
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
                    String price = req.queryParams("price");
                    return null;
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
        get("/Order/:userid/history", (req, res) -> {
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

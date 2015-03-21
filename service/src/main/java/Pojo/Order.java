package Pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015-3-21.
 */
public class Order {
    int orderid;
    User user;
    Hotel neededHotel;

    int price;

    Date createTime;
    int expiretime ;  // in hour

    // "inprogress", "addprice", "done";
    String status;

    // viewed list;
    List<Hotel> viewedList;

    // bid list:
    Map<Integer /*hotelid*/, Integer /*price*/> bidMap;
}

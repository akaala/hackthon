package service;

import Pojo.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015-3-21.
 */
public class OrderService {

    private static Map<Integer, Order> orderMap = new HashMap<>();
    static AtomicInteger id = new AtomicInteger(1);

    public int buy(Order order) {
        int generatedId = generateId();
        order.setOrderid(generatedId);
        orderMap.put(generatedId, order);
        return generatedId;
    }

    private int generateId() {
        return id.incrementAndGet();
    }

    public Order getDoneOrder(int orderId) {
        // todo: get done order by id.
        return null;
    }
}

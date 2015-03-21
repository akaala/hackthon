package service;

import Pojo.Hotel;
import Pojo.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qinf on 2015/3/21.
 */
public class HotelService {

    private static Map<Integer, Hotel> hotelMap = new HashMap<>();
    private static final int HOTEL_INIT_DATA_SIZE = 5;
    private AtomicInteger id = new AtomicInteger(0);

    public HotelService() {
        initHotelData();
    }


    public List<Order> getOrderList(int hotelId) {
        return null;

    }
    private void initHotelData() {
        Hotel hotel1 = initNewHotel("如家",3 , "经济快捷", "上海-徐家汇");
        hotelMap.put(hotel1.getHotelid(), hotel1);

        Hotel hotel2 = initNewHotel("汉庭",3 , "经济快捷", "上海-人民广场");
        hotelMap.put(hotel2.getHotelid(), hotel2);
    }

    /**
     *
     * @param name
     * @param star
     * @param type
     * @param place
     * @return
     */
    private Hotel initNewHotel(String name , int star, String type, String place) {
        Hotel hotel = new Hotel();
        int id = generatedId();
        hotel.setHotelid(id);
        hotel.setType(type);
        hotel.setName(name);
        hotel.setStar(star);
        hotel.setPlace(place);
        return hotel;
    }

    private int generatedId() {
        return id.incrementAndGet();
    }
}

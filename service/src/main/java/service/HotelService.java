package service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import Pojo.Hotel;

/**
 * Created by qinf on 2015/3/21.
 */
public class HotelService {

	private final static HotelService instance = new HotelService();

	public static HotelService getInstance() {
		return instance;
	}

	private Map<Integer, Hotel> hotelMap = new HashMap<>();

	private static final int HOTEL_INIT_DATA_SIZE = 5;

	private AtomicInteger id = new AtomicInteger(0);

	private HotelService() {
		initHotelData();
	}

	private void initHotelData() {
		Hotel hotel = newHotel("如家", 3, "经济型", "上海-徐家汇");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("汉庭", 3, "经济型", "上海-人民广场");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("希尔顿", 5, "商务型", "上海-虹桥");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("Best Western", 4, "度假型", "上海-徐家汇");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("皇冠酒店", 5, "商务型", "上海-人民广场");
		hotelMap.put(hotel.getHotelid(), hotel);
	}

	public Hotel getHotelById(int hotelId) {
		return hotelMap.get(hotelId % HOTEL_INIT_DATA_SIZE);
	}

	/**
	 *
	 * @param name
	 * @param star
	 * @param type
	 * @param place
	 * @return
	 */
	private Hotel newHotel(String name, int star, String type, String place) {
		Hotel hotel = new Hotel();
		int id = generatedId();
		hotel.setHotelid(id);
		hotel.setType(type);
		hotel.setName(name);
		hotel.setStar(star);
		hotel.setLocation(place);
		return hotel;
	}

	private int generatedId() {
		return id.incrementAndGet();
	}
}

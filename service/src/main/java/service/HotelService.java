package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

	private AtomicInteger id = new AtomicInteger(0);

	private HotelService() {
		initHotelData();
	}

	private void initHotelData() {
		Hotel hotel = newHotel("如家", 3, "经济型", "上海-徐家汇", 31.0930, 121.2651, "http://hotels.ctrip.com/hotel/429442.html");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("汉庭", 3, "经济型", "上海-人民广场", 31.1329, 121.2813, "http://hotels.ctrip.com/hotel/1487417.html");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("希尔顿", 5, "商务型", "上海-虹桥", 31.1153, 121.2011, "http://hotels.ctrip.com/hotel/425587.html");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("Best Western", 4, "度假型", "上海-徐家汇", 31.0940, 121.2661,
		      "http://hotels.ctrip.com/hotel/392642.html");
		hotelMap.put(hotel.getHotelid(), hotel);

		hotel = newHotel("皇冠酒店", 5, "商务型", "上海-人民广场", 31.1339, 121.2824, "http://hotels.ctrip.com/hotel/385095.html");
		hotelMap.put(hotel.getHotelid(), hotel);
	}

	public Hotel getHotelById(int hotelId) {
		return hotelMap.get(hotelId);
	}

	/**
	 *
	 * @param name
	 * @param star
	 * @param type
	 * @param place
	 * @return
	 */
	private Hotel newHotel(String name, int star, String type, String place, double lat, double lng, String url) {
		Hotel hotel = new Hotel();
		int id = generatedId();
		hotel.setHotelid(id);
		hotel.setType(type);
		hotel.setName(name);
		hotel.setStar(star);
		hotel.setLocation(place);
		hotel.setLat(lat);
		hotel.setLng(lng);
		hotel.setUrl(url);
		return hotel;
	}

	private int generatedId() {
		return id.incrementAndGet();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Collection<Hotel> getHotels() {
		return hotelMap.values();
	}
}

package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import Pojo.Order;
import Pojo.User;
import com.google.common.collect.ArrayListMultimap;

/**
 * Created by qinf on 2015/3/21.
 */
public class UserService {

	private static final UserService instance = new UserService();

	public static UserService getInstance() {
		return instance;
	}

	private static final int INIT_USER_DATA_SIZE = 5;

	private Map<Integer, User> userMap = new HashMap<>();

	 ArrayListMultimap<Integer /* userid */, Order> userToOrderHistory = ArrayListMultimap.create();


	 private AtomicInteger id = new AtomicInteger(1);

	private UserService() {
		initData();
	}

	public synchronized User getUserById(int userID) {
		User user = userMap.get(userID);
		if (user == null) {
			user = new User();
			user.setUserid(id.incrementAndGet());
			user.setName("test");
			user.setGender("female");
			userMap.put(id.get(), user);
		}
		return user;
	}


	public void addUserOrder(int userId, Order order) {
		 userToOrderHistory.put(userId, order);
	}

	 public List<Order> getUserOrders(int userId) {
		 return userToOrderHistory.get(userId);
	 }

	private void initData() {
		String[] userNameArray = { "刘一鸣", "顾庆", "柯圣", "秦峰", "黄弋简" };
		String[] birth = { "1981－1985", "1986-1990", "1991-1995", "1996-2000" };
		String[] gender = { "男", "女" };
		String[] income = { "10万以下", "10-20万", "20-30万", "30万以上" };
		Random random = new Random();
		for (int i = 0; i < INIT_USER_DATA_SIZE; i++) {
			User user = new User();
			user.setName((userNameArray[i]));
			user.setGender(gender[random.nextInt(gender.length)]);
			user.setBirth(birth[random.nextInt(birth.length)]);
			user.setBidTimes(random.nextInt(10) + 1);
			user.setBidReturnTimes(random.nextInt(user.getBidTimes()));
			user.setIncome(income[random.nextInt(income.length)]);
			user.setUserid(id.incrementAndGet());
			userMap.put(user.getUserid(), user);
		}
	}
}

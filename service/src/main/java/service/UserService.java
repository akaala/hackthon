package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.math3.distribution.BinomialDistribution;

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

	private AtomicInteger id = new AtomicInteger(0);

	private UserService() {
		initData();
	}

	public synchronized User getUserById(int userID) {
		User user = userMap.get(userID);
		return user;
	}

	public void addUserOrder(int userId, Order order) {
		userToOrderHistory.put(userId, order);
	}

	public List<Order> getUserOrders(int userId) {
		return userToOrderHistory.get(userId);
	}

	private void initData() {
		BinomialDistribution distribution = new BinomialDistribution(10, 0.3);
		String[] userNameArray = { "刘一鸣", "顾庆", "柯圣", "秦峰", "黄弋简" };
		String[] birth = { "1981-1985", "1986-1990", "1991-1995", "1996-2000" };
		String[] gender = { "男", "女" };
		String[] income = { "10万以下", "10-20万", "20-30万", "30万以上" };
		String[] tags = { "红二代", "富二代", "不差钱", "抠门", "网购达人", "情侣酒店爱好者", "飞行达人" };
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
			for (int j = 0; j < 5; j++) {
				user.getTags().put(tags[random.nextInt(tags.length)], random.nextInt(10));
			}
			user.getFavorites().put("经济型", random.nextInt(20));
			user.getFavorites().put("商务型", random.nextInt(10));
			user.getFavorites().put("休闲型", random.nextInt(5));
			user.setBidThanOthers(distribution.cumulativeProbability(user.getBidTimes()));
			userMap.put(user.getUserid(), user);
		}
	}

	public Collection<User> getUsers() {
		return userMap.values();
   }
}

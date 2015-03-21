package service;

import Pojo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qinf on 2015/3/21.
 */
public class UserService {

    private static Map<Integer, User> userMap = new HashMap<>();
    public static AtomicInteger id = new AtomicInteger(1);

    public synchronized User getUserById(int userID) {
        User user = userMap.get(userID);
        if(user == null) {
            user = new User();
            user.setUserid(id.incrementAndGet());
            user.setName("test");
            user.setSex("female");
            userMap.put(id.get(), user);
        }
        return user;
    }
}

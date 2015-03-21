package service;

import Pojo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qinf on 2015/3/21.
 */
public class UserService {

    private static final int INIT_USER_DATA_SIZE = 5;
    private static Map<Integer, User> userMap = new HashMap<>();
    private static AtomicInteger id = new AtomicInteger(1);

    public UserService() {
        initData();
    }

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

    private void initData() {
        String[] userNameArray = {"刘一鸣","顾庆", "柯圣", "秦峰", "黄弋简"};
        for(int i = 0 ; i < INIT_USER_DATA_SIZE; i++) {
            User user = new User();
            user.setName((userNameArray[i]));
            user.setSex("male");
            user.setUserid(id.incrementAndGet());
            userMap.put(user.getUserid(), user);
        }
    }
}

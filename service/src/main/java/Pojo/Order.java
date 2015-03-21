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

    int price;
    int star;
    String type;
    String place;


    Date createTime;
    int expiretime ;  // in hour

    // "inprogress", "addprice", "done";
    String status;

    // viewed list;
    List<Hotel> viewedList;

    // bid list:
    Map<Integer /*hotelid*/, Integer /*price*/> bidMap;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getNeededHotel() {
        return neededHotel;
    }

    public void setNeededHotel(Hotel neededHotel) {
        this.neededHotel = neededHotel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Hotel> getViewedList() {
        return viewedList;
    }

    public void setViewedList(List<Hotel> viewedList) {
        this.viewedList = viewedList;
    }

    public Map<Integer, Integer> getBidMap() {
        return bidMap;
    }

    public void setBidMap(Map<Integer, Integer> bidMap) {
        this.bidMap = bidMap;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

package Pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015-3-21.
 */
public class Order {
	private int orderid;

	private User user;

	private Date createTime;

	private int expiretime; // in minutes

	private int dealPrice; // 成交价

	// "inprogress", "addprice", "done";
	private String status;

	// viewed list;
	private List<Hotel> viewedList;

	// bid list:
	private Map<Integer /* hotelid */, Integer /* price */> bidMap;

	private OrderRequest orderRequest;

	public OrderRequest getOrderRequest() {
		return orderRequest;
	}

	public void setOrderRequest(OrderRequest orderRequest) {
		this.orderRequest = orderRequest;
	}

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

	public int getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(int dealPrice) {
		this.dealPrice = dealPrice;
	}

}

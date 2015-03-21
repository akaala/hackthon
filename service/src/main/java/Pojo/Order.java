package Pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Administrator on 2015-3-21.
 */
public class Order {
	public enum OrderStatus {
		inbid, extrabid, done
	}

	private int orderid;

	private User user;

	private Date createTime;

	private int expiretime; // in minutes

	private int dealPrice; // 成交价

	private Date dealTime;

	// "inprogress", "addprice", "done";
	private OrderStatus status;

	// viewed list;
	private Map<Integer, Hotel> viewedList = new HashMap<>();

	// bid list:
	private Map<Integer /* hotelid */, List<HotelBidRequest> /* price */> bidMap = new HashMap<>();

	private UserBidRequest orderRequest;

	private HotelBidRequest winningBid;

	private HotelBidRequest bestLosingBid;

	public HotelBidRequest getBestLosingBid() {
		return bestLosingBid;
	}

	public void setBestLosingBid(HotelBidRequest bestLosingBid) {
		this.bestLosingBid = bestLosingBid;
	}

	public HotelBidRequest getWinningBid() {
		return winningBid;
	}

	public void setWinningBid(HotelBidRequest winningBid) {
		this.winningBid = winningBid;
	}

	public UserBidRequest getHotelRequest() {
		return orderRequest;
	}

	public void setHotelRequest(UserBidRequest orderRequest) {
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Map<Integer, Hotel> getViewedList() {
		return viewedList;
	}

	public void setViewedList(Map<Integer, Hotel> viewedList) {
		this.viewedList = viewedList;
	}

	public void addHotelBidRequest(HotelBidRequest request) {
		if (!bidMap.containsKey(request.getHotelId())) {
			bidMap.put(request.getHotelId(), new ArrayList<HotelBidRequest>());
		}
		List<HotelBidRequest> list = bidMap.get(request.getHotelId());
		list.add(request);
	}

	public HotelBidRequest getHotelBidRequest(int hotelBidId) {
		for (List<HotelBidRequest> list : bidMap.values()) {
			for (HotelBidRequest bidRequest : list) {
				if (bidRequest.getBidId() == hotelBidId) {
					return bidRequest;
				}
			}
		}
		return null;
	}

	public Map<Integer, List<HotelBidRequest>> getBidMap() {
		return bidMap;
	}

	public void setBidMap(Map<Integer, List<HotelBidRequest>> bidMap) {
		this.bidMap = bidMap;
	}

	public int getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(int dealPrice) {
		this.dealPrice = dealPrice;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean isBidTimeout() {
		return System.currentTimeMillis() > createTime.getTime() + expiretime * 60L * 1000;
	}

	public boolean isExtraBidTimeout() {
		// timeout 1min after normal bid
		return System.currentTimeMillis() > createTime.getTime() + expiretime * 60L * 1000 + 60 * 1000;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

}

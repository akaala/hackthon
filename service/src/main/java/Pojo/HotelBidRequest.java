package Pojo;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HotelBidRequest {
	private int bidId;
	
	private Hotel hotel;
	
	private int extraPrice;

	private String comment;

	private Date createDate;

	public int getExtraPrice() {
		return extraPrice;
	}

	public void setExtraPrice(int extraPrice) {
		this.extraPrice = extraPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Hotel getHotel() {
	   return hotel;
   }

	public void setHotel(Hotel hotel) {
	   this.hotel = hotel;
   }
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean satify(Order order) {
	   return extraPrice == 0;
   }

	public int getBidId() {
	   return bidId;
   }

	public void setBidId(int bidId) {
	   this.bidId = bidId;
   }
}

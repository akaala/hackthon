package Pojo;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HotelBidRequest {
	private int hotelId;
	
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

	public int getHotelId() {
	   return hotelId;
   }

	public void setHotelId(int hotelId) {
	   this.hotelId = hotelId;
   }
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean satify(Order order) {
	   return extraPrice == 0;
   }
}

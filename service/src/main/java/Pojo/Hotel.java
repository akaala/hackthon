package Pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Administrator on 2015-3-21.
 */
public class Hotel {
	private int hotelid;

	private String name;

	private Integer star;

	private String type;

	private String location;

	private double lat;

	private double lng;

	public int getHotelid() {
		return hotelid;
	}

	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStar() {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String place) {
		this.location = place;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}

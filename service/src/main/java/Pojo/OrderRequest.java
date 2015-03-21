package Pojo;

public class OrderRequest {
	private int price;

	// 1, 2, 3, 4, 5
	private int star;

	// 经济型，商务型，度假型
	private String type;

	private String location;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String place) {
		this.location = place;
	}
}

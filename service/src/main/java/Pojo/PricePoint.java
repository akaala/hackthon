package Pojo;
public class PricePoint {
	private double price;

	private double probability;

	public PricePoint(double price, double probability) {
		this.price = price;
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return this.price + "->" + this.probability;
	}
}

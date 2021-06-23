package it.polimi.tiw.bean;

import java.io.Serializable;

public class SellerBean implements Serializable {

	private String id;
	private String seller_name;
	private float seller_rating;
	private float price_threshold;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

	public float getSeller_rating() {
		return seller_rating;
	}

	public void setSeller_rating(float seller_rating) {
		this.seller_rating = seller_rating;
	}

	public float getPrice_threshold() {
		return price_threshold;
	}

	public void setPrice_threshold(float price_threshold) {
		this.price_threshold = price_threshold;
	}
}

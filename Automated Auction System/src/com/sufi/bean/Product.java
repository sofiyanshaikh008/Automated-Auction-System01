package com.sufi.bean;


public class Product {
	
	private int id;
	private String name;
	private int sellerId;
	private int price;
	private int quantity;
	private boolean status;
	private String category;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int id, String name, int sellerId, int price, int quantity, boolean status, String category) {
		super();
		this.id = id;
		this.name = name;
		this.sellerId = sellerId;
		this.price = price;
		this.quantity = quantity;
		this.status = status;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", sellerId=" + sellerId + ", price=" + price + ", quantity="
				+ quantity + ", status=" + status + ", category=" + category + "]";
	}
	
	
	
	
	
}

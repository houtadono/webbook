package com.example.webbook.entity;

public class OrderLine {
	private int oid;
	private int bid;
	private int price;
	private int quantity;
	private int totalMoney;
	private String image;
	private String title;
	private String author;
	private String category;
	
	public OrderLine(int oid, int bid, int quantity,int totalMoney) {
		super();
		this.oid = oid;
		this.bid = bid;
		this.quantity = quantity;
		this.totalMoney = totalMoney;
	}
	
	

	public OrderLine(int oid, int bid,  String image, String title, String author,String category,int price,int quantity,int totalMoney) {
		super();
		this.oid = oid;
		this.bid = bid;
		this.quantity = quantity;
		this.image = image;
		this.title = title;
		this.author = author;
		this.category = category;
		this.price = price;
		this.totalMoney = totalMoney;
	}



	public OrderLine( String image, String title, String author,int quantity) {
		super();
		this.quantity = quantity;
		this.image = image;
		this.title = title;
		this.author = author;
	}   
	
	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public String getCategory() {
		return category;
	}

    

	public int getTotalMoney() {
		return totalMoney;
	}



	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "OrderLine [oid=" + oid + ", bid=" + bid + ", quantity=" + quantity + ", image=" + image + ", title="
				+ title + ", author=" + author + "]";
	}
	
}

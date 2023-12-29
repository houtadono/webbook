package com.example.webbook.entity;

import java.sql.Date;

public class Book {
	private int stt;
	private int id;
	private String title;
	private String author;
	private String description;
	private Date day;
	private int page;
	private int cid;
	private String category;
	private String image;
	private int quantity;
	private int sold;
	private int rating;
	private int price;
	public Book() {
		super();
	}
	
	
	public Book(String title, String image, int sold, int rating) {
		super();
		this.title = title;
		this.image = image;
		this.sold = sold;
		this.rating = rating;
	}

	public Book(int id, String title, String author, String description, Date day, int page, int cid, String image,
			int quantity, int sold,int price) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
		this.day = day;
		this.page = page;
		this.cid = cid;
		this.image = image;
		this.quantity = quantity;
		this.sold = sold;
		this.price = price;
	}
	public Book(String title, String author, String description, Date day, int page, int cid, String image,
			int quantity, int sold,int price) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
		this.day = day;
		this.page = page;
		this.cid = cid;
		this.image = image;
		this.quantity = quantity;
		this.sold = sold;
		this.price = price;
	}
	
	
	
	public Book(String title, String author, String description, Date day, int page, int cid, String image) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
		this.day = day;
		this.page = page;
		this.cid = cid;
		this.image = image;
	}


	public Book(int stt,int id, String title, String author, Date day, int page,int cid, String category, int sold) {
		super();
		this.id = id;
		this.stt = stt;
		this.title = title;
		this.author = author;
		this.day = day;
		this.page = page;
		this.cid = cid;
		this.category = category;
		this.sold = sold;
	}

	public Book(int id, String title, String author, String description, Date day, int page, String category,
			String image, int quantity, int sold,int price) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
		this.day = day;
		this.page = page;
		this.category = category;
		this.image = image;
		this.quantity = quantity;
		this.sold = sold;
		this.price = price;
	}
	
	
	
	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getStt() {
		return stt;
	}


	public void setStt(int stt) {
		this.stt = stt;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description + ", day="
				+ day + ", page=" + page + ", cid=" + cid + ", image=" + image + ", quantity=" + quantity + ", sold="
				+ sold + "]";
	}
	
	
}

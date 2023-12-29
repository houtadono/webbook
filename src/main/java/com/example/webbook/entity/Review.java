package com.example.webbook.entity;

public class Review {
	private int uid;
	private int bid;
	private String username;
	private String content;
	private int rating;
	private String day;
	
	public Review() {
		super();
	}
	

	public Review(String username, String content, int rating, String day) {
		super();
		this.username = username;
		this.content = content;
		this.rating = rating;
		this.day = day;
	}


	public Review(int uid, int bid, String content, int rating, String day) {
		super();
		this.uid = uid;
		this.bid = bid;
		this.content = content;
		this.rating = rating;
		this.day = day;
	}
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Review [uid=" + uid + ", bid=" + bid + ", content=" + content + ", rating=" + rating + ", day=" + day
				+ "]";
	}
	
	
}

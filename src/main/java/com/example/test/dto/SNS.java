package com.example.test.dto;

public class SNS {
	private int articleId;
	private String userId;
	private String date;
	private String picture;
	private String contents;
	
	SNS(){}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "SNS [articleId=" + articleId + ", userId=" + userId + ", date=" + date + ", picture="
				+ picture + ", contents=" + contents + "]";
	}
}
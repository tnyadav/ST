package com.volvr.beans;

public class StripperReviews {
	 private String review_id;
	 private String provider_user_id;
	 private String review_rating;
	 private String review_comments;
	 private String review_writed;
	 private String review_is_show;
	 private String review_provider_name;
	 
	 public String getReview_provider_name() {
		return review_provider_name;
	}
	public void setReview_provider_name(String review_provider_name) {
		this.review_provider_name = review_provider_name;
	}
	public String getReview_provider_thumb() {
		return review_provider_thumb;
	}
	public void setReview_provider_thumb(String review_provider_thumb) {
		this.review_provider_thumb = review_provider_thumb;
	}
	private String review_provider_thumb;
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public String getProvider_user_id() {
		return provider_user_id;
	}
	public void setProvider_user_id(String provider_user_id) {
		this.provider_user_id = provider_user_id;
	}
	public String getReview_rating() {
		return review_rating;
	}
	public void setReview_rating(String review_rating) {
		this.review_rating = review_rating;
	}
	public String getReview_comments() {
		return review_comments;
	}
	public void setReview_comments(String review_comments) {
		this.review_comments = review_comments;
	}
	public String getReview_writed() {
		return review_writed;
	}
	public void setReview_writed(String review_writed) {
		this.review_writed = review_writed;
	}
	public String getReview_is_show() {
		return review_is_show;
	}
	public void setReview_is_show(String review_is_show) {
		this.review_is_show = review_is_show;
	}
}

package com.volvr.beans;

public class OfferBean {
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getOffer_start_date() {
		return offer_start_date;
	}
	public void setOffer_start_date(String offer_start_date) {
		this.offer_start_date = offer_start_date;
	}
	public String getOffer_end_date() {
		return offer_end_date;
	}
	public void setOffer_end_date(String offer_end_date) {
		this.offer_end_date = offer_end_date;
	}
	public String getOffer_desc() {
		return offer_desc;
	}
	public void setOffer_desc(String offer_desc) {
		this.offer_desc = offer_desc;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getClub_id() {
		return club_id;
	}
	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}
	public String getClub_name() {
		return club_name;
	}
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	public String getClub_thumb_pic() {
		return club_thumb_pic;
	}
	public void setClub_thumb_pic(String club_thumb_pic) {
		this.club_thumb_pic = club_thumb_pic;
	}
	private String offer_start_date;
	private String offer_end_date;
	private String offer_desc;
	private String is_active;
	private String club_id;
	private String club_name;
	private String club_thumb_pic;
	private String offer_id;

}

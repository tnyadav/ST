package com.volvr.beans;

public class WorkingAt {

	 private String club_zip_code;
     private String club_city;
     private String club_contact_number;
     private String club_country;
     private String club_address;
     private String club_state;
     private String club_name;
     private String club_id;
     private String club_user_id;
     
	public String getClub_user_id() {
		return club_user_id;
	}
	public void setClub_user_id(String club_user_id) {
		this.club_user_id = club_user_id;
	}
	public String getClub_id() {
		return club_id;
	}
	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}
	
	public String getClub_zip_code() {
		return club_zip_code;
	}
	public void setClub_zip_code(String club_zip_code) {
		this.club_zip_code = club_zip_code;
	}
	public String getClub_city() {
		return club_city;
	}
	public void setClub_city(String club_city) {
		this.club_city = club_city;
	}
	public String getClub_contact_number() {
		return club_contact_number;
	}
	public void setClub_contact_number(String club_contact_number) {
		this.club_contact_number = club_contact_number;
	}
	public String getClub_country() {
		return club_country;
	}
	public void setClub_country(String club_country) {
		this.club_country = club_country;
	}
	public String getClub_address() {
		return club_address;
	}
	public void setClub_address(String club_address) {
		this.club_address = club_address;
	}
	public String getClub_state() {
		return club_state;
	}
	public void setClub_state(String club_state) {
		this.club_state = club_state;
	}
	public String getClub_name() {
		return club_name;
	}
	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}
	@Override
    public String toString() {
        return this.club_name;
    }
}

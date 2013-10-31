package com.volvr.beans;

import android.widget.EditText;

public class OtherInfo {

	private	EditText name;
    private	EditText number;
    private	EditText email;
    private	EditText address;
    
    
	public EditText getName() {
		return name;
	}
	public void setName(EditText name) {
		this.name = name;
	}
	public EditText getNumber() {
		return number;
	}
	public void setNumber(EditText number) {
		this.number = number;
	}
	public EditText getAddress() {
		return address;
	}
	public void setAddress(EditText address) {
		this.address = address;
	}
	public EditText getEmail() {
		return email;
	}
	public void setEmail(EditText email) {
		this.email = email;
	}
	
}

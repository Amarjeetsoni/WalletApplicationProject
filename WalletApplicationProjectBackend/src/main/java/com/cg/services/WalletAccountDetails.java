package com.cg.services;

public class WalletAccountDetails {
     private String user_name;
     private String firstName;
     private String mobile_no;
     
     public WalletAccountDetails() {
    	 
     }
     
     
	public WalletAccountDetails(String user_name, String firstName, String mobile_no) {
		this.user_name = user_name;
		this.firstName = firstName;
		this.mobile_no = mobile_no;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
     
}

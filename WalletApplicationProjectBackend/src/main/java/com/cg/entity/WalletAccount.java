package com.cg.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class WalletAccount {
  
	   @Column(length = 30)
	   @NotNull(message = "FirstName Cannot be Omitted")
	   @NotEmpty(message = "FirstName Cannot be left Empty")
	   private String firstName;
	   
	   @Column(length = 30)
	   @NotNull(message = "lastName Cannot be Omitted")
	   @NotEmpty(message = "lastName Cannot be left Empty")
	   private String lastName;
	   
	   @Column(length = 30)
	   private String middleName;
	   
	   @Column(length = 10)
	   @NotNull(message = "Age Cannot be Omitted")
	   @NotEmpty(message = "Age Cannot be left Empty")
	   private String DOB;
	   
	   @Column(length = 10)
	   @NotNull(message = "gender Cannot be Omitted")
	   @NotEmpty(message = "gender Cannot be left Empty")
	   private String gender;
	   
	   
	   @Column(length = 11)
	   @NotNull(message = "Mobile Number Cannot be Omitted")
	   @NotEmpty(message = "Mobile Number Cannot be left Empty")
	   private String mobile_no;
	   
	   @Column(length = 30)
	   @NotNull(message = "Email Cannot be Omitted")
	   @NotEmpty(message = "Email Cannot be left Empty")
	   private String emailId;
	   
	   @Id
	   @Column(length = 30)
	   @NotNull(message = "UserName Cannot be Omitted")
	   @NotEmpty(message = "UserName Cannot be left Empty")
	   private String user_name;
	   
	   @Column(length = 30)
	   @NotNull(message = "password Cannot be Omitted")
	   @NotEmpty(message = "password Cannot be left Empty")
	   private String password;
	   
	   @NotNull(message = "balance Cannot be Omitted")
	   private double Balance;
	   
	   @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	   @JsonIgnore
	   private List<WalletMoneyStatement> statements = new ArrayList<WalletMoneyStatement>();

	   public WalletAccount() {
		   
	   }
	   
	   


	public WalletAccount(
			@NotNull(message = "FirstName Cannot be Omitted") @NotEmpty(message = "FirstName Cannot be left Empty") String firstName,
			@NotNull(message = "lastName Cannot be Omitted") @NotEmpty(message = "lastName Cannot be left Empty") String lastName,
			String middleName,
			@NotNull(message = "Age Cannot be Omitted") @NotEmpty(message = "Age Cannot be left Empty") String dOB,
			@NotNull(message = "gender Cannot be Omitted") @NotEmpty(message = "gender Cannot be left Empty") String gender,
			@NotNull(message = "Mobile Number Cannot be Omitted") @NotEmpty(message = "Mobile Number Cannot be left Empty") String mobile_no,
			@NotNull(message = "Email Cannot be Omitted") @NotEmpty(message = "Email Cannot be left Empty") String emailId,
			@NotNull(message = "UserName Cannot be Omitted") @NotEmpty(message = "UserName Cannot be left Empty") String user_name,
			@NotNull(message = "password Cannot be Omitted") @NotEmpty(message = "password Cannot be left Empty") String password,
			@NotNull(message = "balance Cannot be Omitted") double balance) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		DOB = dOB;
		this.gender = gender;
		this.mobile_no = mobile_no;
		this.emailId = emailId;
		this.user_name = user_name;
		this.password = password;
		Balance = balance;
	}




	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getDOB() {
		return DOB;
	}


	public void setDOB(String dOB) {
		DOB = dOB;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMobile_no() {
		return mobile_no;
	}


	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public double getBalance() {
		return Balance;
	}


	public void setBalance(double balance) {
		Balance = balance;
	}
	
	public List<WalletMoneyStatement> getStatements() {
		return statements;
	}


	public void setStatements(List<WalletMoneyStatement> statements) {
		this.statements = statements;
	}
	
	public void addStatements(WalletMoneyStatement ws) {
		ws.setAccount(this);
		this.getStatements().add(ws);
	}
	
	@Override
	public String toString() {
		return "WalletAccountBean [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", DOB=" + DOB + ", gender=" + gender + ", mobile_no=" + mobile_no + ", emailId=" + emailId + ", user_name="
				+ user_name + ", password=" + password + ", Balance=" + Balance + "]";
	}
	   
	
}

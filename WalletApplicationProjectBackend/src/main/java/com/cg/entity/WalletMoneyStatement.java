package com.cg.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class WalletMoneyStatement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transaction_id;
	
	@Column(length = 250)
	@NotNull(message = "statement Cannot be Omitted")
	@NotEmpty(message = "statement Cannot be left Empty")
    private String statement;
	
	@Column(length = 50)
	@NotNull(message = "transaction_date Cannot be Omitted")
	@NotEmpty(message = "transaction_date Cannot be left Empty")
    private String  transaction_date;
	
	private int actionType;
	private String action;
	
	@ManyToOne
	@JoinColumn(name = "user_name")
	private WalletAccount account;
    
	
	public WalletMoneyStatement() {
		
	}
	public WalletMoneyStatement(String statement, WalletAccount account) {
//		this.user_name = user_name;
		this.statement = statement;
		this.account = account;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public WalletAccount getAccount() {
		return account;
	}

	public void setAccount(WalletAccount account) {
		this.account = account;
	}
	
	public String getDate() {
		return transaction_date;
	}
	public void setDate(String date) {
		this.transaction_date = date;
	}
	
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String toString() {
		return "WalletMoneyStatement [transaction_id=" + transaction_id + ", statement=" + statement + ", account="
				+ account + "]";
	}
	
	
	
	    
}

package com.cg.dao;

import java.util.List;

import com.cg.entity.WalletAccount;
import com.cg.entity.WalletMoneyStatement;



public interface WalletDao {
	 boolean createAccount(WalletAccount walletAccount) throws Exception;
	 boolean depositBalance(double money, String username, String password);
	 boolean foundTransferAmount(String username1, String password, String username2, double balance);
	 List<WalletMoneyStatement> printallTransation(String username, String password);
	 public double showBalance(String user_name, String password);
	 boolean withdrawAmount(Double money, String username, String password);
	 boolean verifyLoginCredintial(String user_name, String password);
	 boolean checkUserName(String userName);
	 List<WalletAccount> getAllUser();
	 public WalletAccount getUser(String username, String password);
	 boolean updateUser(WalletAccount walletAccount);
	 WalletAccount getUserByUserName(String username);
}

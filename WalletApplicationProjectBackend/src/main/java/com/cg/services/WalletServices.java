package com.cg.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cg.entity.WalletAccount;
import com.cg.entity.WalletMoneyStatement;


public interface WalletServices {
	 public ResponseEntity<Object> createAccount(WalletAccount walletAccount) throws WalletAccountServiceException, Exception;
	 boolean depositBalance(double money, String username, String password) throws WalletAccountServiceException;
	 boolean fundTransferAmount(String username1, String password, String username2, double balance) throws WalletAccountServiceException, Exception;
	 List<WalletMoneyStatement> printallTransation(String username, String password) throws WalletAccountServiceException, Exception;
//	 public double showBalance(String user_name, String password);
	 public boolean withdrawAmount(Double money, String username, String password) throws WalletAccountServiceException, Exception;
	 public boolean verifyLoginCredintial(String userName, String password) throws WalletAccountServiceException;
	 public boolean checkUserName(String userName) throws WalletAccountServiceException, Exception;
	 public boolean varifyAccountDetails(String emailId, String mobNumber);
	 public boolean check_name(String name);
	 List<WalletAccountDetails> getAllUser() throws WalletAccountServiceException;
	 public WalletAccount getUser(String username, String password) throws WalletAccountServiceException;
	 boolean varifyMobile(String mobile) throws WalletAccountServiceException, Exception;
	boolean varifyEmail(String emailId) throws WalletAccountServiceException, Exception;
	boolean updateUser(WalletAccount walletAccount) throws WalletAccountServiceException;
	WalletAccount getUserByUserName(String username) throws WalletAccountServiceException;
}

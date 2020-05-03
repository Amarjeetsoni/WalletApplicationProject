package com.cg.services;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dao.WalletDao;
import com.cg.entity.WalletAccount;
import com.cg.entity.WalletMoneyStatement;

@Transactional
@Service("walletServiceImple")
public class WalletServicesImpl implements WalletServices{
	
	/*
	 * @Autowired is used to
	 * Inject the dependency of walletService class into WalletServicesImpl class.
	 * It internally uses setter or constructor to inject dependency.  
	 */
	@Autowired
	private WalletDao implement;
	
	
	/*
	 * Logger used to Record unusual circumstances or error that may be happening in the program.
	 * also use to get info what is going in the application.
	 */
	Logger logger = LoggerFactory.getLogger(WalletServicesImpl.class);
	
	
	/*
	 * This method is used to insert a new recored in the table.
	 * @param walletAccount This is parameter of WalletAccount type of createAccount method.
	 * @return ResponceEntity<Object> this return the String message that is to be displayed on to front end.
	 * this method throws WalletAccountServiceException which is a custom exception class as well as Exception class.
	 */
	@Override
	public ResponseEntity<Object> createAccount(WalletAccount walletAccount) throws Exception{
		try {
		boolean isCreated = implement.createAccount(walletAccount);
		if(isCreated) {
			logger.info("Wallet account successfully created...!!");
			return new ResponseEntity<Object>(true, HttpStatus.OK);
		}
		else {
			logger.error("Internal server Error...!!");
			throw new WalletAccountServiceException("Details already exist...");
		}
		}catch(Exception exception) {
			throw new Exception("Internal Server Error...");
		}
	}

	
	/*
	 * This method is used to update balance when user deposit money it will update users balance.
	 * @param money this is first parameter of depositBalance method which is of double type.
	 * @param username this is second parameter of depositeBalance method which is of String type.
	 * @param password this is third parameter of deposieBalance method which is of String type.
	 * @return boolean this return true if balance successfully deposited otherwise throw exception.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public boolean depositBalance(double money, String username, String password) throws WalletAccountServiceException {
		if(money > 0 && money <= 50000 && verifyLoginCredintial(username,password)) {
			logger.info("money deposit in your account...!!!");
			boolean depositStatus = implement.depositBalance(money, username, password);
			return depositStatus;
		}
		else {
			logger.error("Error occured while deposing money...!!!");
			throw new WalletAccountServiceException("Money Should Not be Greater then 50000 or less then 0...");
		}
	}

	
	
	/*
	 * This method is used to transfer money from one account to another account(fund Transfer).
	 * @param username1 this is first parameter of fundTransferAmount method which is of String type.
	 * @param password this is second parameter of fundTransferAmount method which is of String type.
	 * @param username2 this is third parameter of fundTransferAmount method which is of String type.
	 * @param balance this is fourth parameter of fundTransferAmount method which is of double type.
	 * @return boolean this return true if balance successfully deposited from your account and credited into other account otherwise throw exception.
	 * this method throws Exception which is subclass of throwable class.
	 */
	@Override
	public boolean fundTransferAmount(String username1, String password, String username2, double balance) throws Exception {
		if(username1 == null || username2 == null || password == null || balance <= 0) {
			throw new Exception("Null Values/ balance less then or zero not allowed...");
		}
		if(!check_name(username2)) {
			logger.error("Reciver UserName Not Valid...");
			throw new Exception("UserName Does not Valid...!");
		}
		if(username1.equals(username2)) {
			logger.error("Reciver UserName must be diffrent...");
			throw new Exception("Username must be diffrent than yours..");
		}
		   verifyLoginCredintial(username1,password);
			boolean ans = implement.foundTransferAmount(username1, password, username2, balance);
			if(ans) {
				logger.info("Money transfered...");
			    return true;
			}
			else {
				logger.error("Balance is low...");
				throw new WalletAccountServiceException("Your Account Balance is Low...");
			}
		
	}

	
	/*
	 * This method is used to print all transaction of a specific user.
	 * @param username this is first parameter of printallTransation method which is of String type.
	 * @param password this is second parameter of printallTransation method which is of String type.
	 * @return List<WalletMoneyStatement> this return all transaction made by a provided account. it return the list of object which contain some more information.
	 * this method throws Exception which is subclass of throwable class.
	 */
	@Override
	public List<WalletMoneyStatement> printallTransation(String username, String password) throws Exception {
		if(verifyLoginCredintial(username, password)) {
           List<WalletMoneyStatement> allTransation = implement.printallTransation(username, password);
           if(allTransation.size() == 1) {
        	   logger.error("No transaction is there...");
        	   throw new WalletAccountServiceException("No Transaction is available");
           }
           else {
        	   logger.info("list of transaction...");
        	   return allTransation;
           }
		}
		else {
			logger.error("username/password is worng..");
           throw new Exception("UserName/Password is Wrong...");
		}
	}

	
	/*
	 * This method is used to withdraw money from a given account details.
	 * @param money this is first parameter of withdrawAmount method which is of double type.
	 * @param username this is second parameter of withdrawAmount method which is of String type.
	 * @param password this is third parameter of withdrawAmount method which is of String type.
	 * @return boolean this return true if money successfully withdraw from your account otherwise throw some exception.
	 * this method throws Exception which is subclass of throwable class.
	 */
	@Override
	public boolean withdrawAmount(Double money, String username, String password) throws Exception {
		if(money > 0 && verifyLoginCredintial(username,password)){
			boolean isWithdraw = implement.withdrawAmount(money, username, password);
			if(isWithdraw)
			{
				logger.info("Money withdraw successfully...");
				return true;
			}
			else {
				logger.error("Not Enough amount...");
				throw new WalletAccountServiceException("Not Enough balance In Your account...");
			}
		}
		else {
			logger.error("Money should be greater then 0...");
			throw new WalletAccountServiceException("Money Should be greater then 0...!");
		}
		
	}
	
	
	/*
	 * This method is used to update details of user.
	 * @param walletAccount this is first parameter of updateUser which contains details of updated user.
	 * @return boolean this return true if detail updated successfully.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public boolean updateUser(WalletAccount walletAccount) throws WalletAccountServiceException {
	    try {
		       implement.updateUser(walletAccount);
	    	   logger.info("Detail updated successfully...");
	    	   return true;
	       }
	       catch(Exception exception) {
	    	   logger.error("Internal server error while updating details...");
	    	   throw new WalletAccountServiceException();
	       }
	}
	
	/*
	 * This method is used to verify Login Credentials details of user.
	 * @param username this is first parameter to verifyLoginCredintial method which is of String type.
	 * @param password this is second parameter to verifyLoginCredintial method which is of String type.
	 * @return boolean this return true if details are verified otherwise throw some error.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public boolean verifyLoginCredintial(String userName, String password) throws WalletAccountServiceException {
		  
		   if(userName == null || password == null || userName.equals("") || password.equals("")) {
			   throw new WalletAccountServiceException("Null value is not allowed...");
		   }
		    boolean bool = implement.verifyLoginCredintial(userName, password);
		    if(bool) {
		    	logger.info("Login successfully...");
		    	return true;
		    }
		    else {
		    	logger.error("username/password is worng...");
	    	   throw new WalletAccountServiceException("Not a valid credential...");
	       }
	}
	
	
	/*
	 * This method is used to check whether userName is already present in database or not.
	 * @param username this is first parameter to checkUserName method which is of String type.
	 * @return boolean this return false when user is not there in data base otherwise throw Exception.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public boolean checkUserName(String userName) throws Exception {
		  if(userName == null || userName == "")
		  {
			  throw new Exception("null values are not allowed...");
		  }
		  
		  if(implement.checkUserName(userName))
		   {
			   logger.error("username already exist...");
			   throw new WalletAccountServiceException("userName Already exist Try for another!!...");
			   
		   }
		   else {
			   logger.error("username valid...");
			   return false;
		  }
	   }
	
	
	/*
	 * This method is used to check whether EmailId or Mobile Number is already present in database or not.
	 * @param emailId this is first parameter of varifyAccountDetails method which is of String type.
	 * @param mobNumber this is second parameter of varifyAccountDetails method which is of String type.
	 * @return boolean this return false when details are not present in database otherwise returns true.
	 */
	@Override
	public boolean varifyAccountDetails(String emailId, String mobNumber) {
		   List<WalletAccount> allUser = implement.getAllUser();
		   for(int i = 0; i < allUser.size(); i++) {
			   if(allUser.get(i).getMobile_no().equalsIgnoreCase(mobNumber)  || allUser.get(i).getEmailId().equalsIgnoreCase(emailId)) {
				   logger.error("Deatils already exist...");
				   return false;
			   }
		   }
		   logger.info("Details are valid...");
		   return true;
	}
	
	
	/*
	 * This method is used to check whether EmailId is already present in database or not.
	 * @param emailId this is first parameter of varifyEmail method which is of String type.
	 * @return boolean this return true when details are not present in database otherwise throw exception with some message.
	 * this method throws Exception which is subclass of throwable class.
	 */
	@Override
	public boolean varifyEmail(String emailId) throws Exception {
		
		if(emailId == null) {
			throw new Exception("Null values are not allows...");
		}
		List<WalletAccount> allUser = implement.getAllUser();
		for(int i = 0; i < allUser.size(); i++) {
			   if(allUser.get(i).getEmailId().equalsIgnoreCase(emailId)) {
				   logger.error("Email Id already Exist...");
				   throw new WalletAccountServiceException("Email Already Registered...");
			   }
		   }
		logger.info("Email is valid...");
		return true;
	}
	
	
	/*
	 * This method is used to check whether Mobile number is already present in database or not.
	 * @param mobile this is first parameter of varifyMobile method which is of String type.
	 * @return boolean this return true when details are not present in database otherwise throw exception with some message.
	 * this method throws Exception which is subclass of throwable class.
	 */
	@Override
	public boolean varifyMobile(String mobile) throws Exception {
		
		if(mobile == null) {
			throw new Exception("Null values are not allowed..");
		}
		List<WalletAccount> allUser = implement.getAllUser();
		for(int i = 0; i < allUser.size(); i++) {
			   if(allUser.get(i).getMobile_no().equalsIgnoreCase(mobile)) {
				   logger.error("Mobile number is already exist...");
				   throw new WalletAccountServiceException("Mobile number Already Registered...");
			   }
		   }
		logger.error("Mobile number us valid...");
		return true;
	}

	
	/*
	 * This method is used to check whether userName is already present in database or not.
	 * @param name this is first parameter of check_name method which is of String type.
	 * @return boolean this return true when details are not present in database otherwise return false.
	 */
	@Override
	public boolean check_name(String name) {
		   boolean isExist = implement.checkUserName(name);
	       return isExist;
	   }
	
	
	/*
	 * This method is used to get details of all user present in database.
	 * @return List<WalletAccount> this return all users details as an object form if present otherwise throw an exception. 
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public List<WalletAccountDetails> getAllUser() throws WalletAccountServiceException{
		System.out.println("we are here nro1...");
		List<WalletAccount> allUsers =  implement.getAllUser();
		System.out.println("we are here nro2..");
		if(allUsers.isEmpty()) {
			logger.error("No User Available...");
			throw new WalletAccountServiceException();
		}
		else {
			List<WalletAccountDetails> walletAccountDetailsList = new ArrayList<>();
			
			for(int i = 0; i < allUsers.size(); i++) {
				
				WalletAccountDetails walletAccountDetails = new WalletAccountDetails();
				if(allUsers.get(i).getMiddleName() != null) {
					walletAccountDetails.setFirstName(allUsers.get(i).getFirstName() + " " + allUsers.get(i).getMiddleName() + " " + allUsers.get(i).getLastName());
				}
				else {
					walletAccountDetails.setFirstName(allUsers.get(i).getFirstName() + " " + allUsers.get(i).getLastName());
				}
				walletAccountDetails.setMobile_no(allUsers.get(i).getMobile_no());
				walletAccountDetails.setUser_name(allUsers.get(i).getUser_name());
				walletAccountDetailsList.add(walletAccountDetails);
			}
			return walletAccountDetailsList;
		}
	}

	
	
	/*
	 * This method is used to get details of a specific user.
	 * @param username this is first parameter of getUser method which is of String type.
	 * @param password this is second parameter of getUser method which is of string type.
	 * @return WalletAccount this return detail of a specific user in form of an object otherwise throw an exception with some message.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public WalletAccount getUser(String username, String password) throws WalletAccountServiceException {
        if(username.equals(null) || password.equals(null)) {
        	logger.error("Some field are null...");
        	throw new WalletAccountServiceException();
        }
		WalletAccount walletAccount = implement.getUser(username, password);
		if(walletAccount == null) {
			logger.error("No user found...");
			return null;
		}
		else {
			logger.info("User details...");
		   return walletAccount;
		}
	}
     
	
	
	/*
	 * This method is used to get details of a specific user.
	 * @param username this is first parameter of getUserByUserName method which is of String type.
	 * @return WalletAccount this return detail of a specific user in form of an object otherwise throw an exception with some message.
	 * this method throws WalletAccountServiceException which is a custom exception class.
	 */
	@Override
	public WalletAccount getUserByUserName(String username) throws WalletAccountServiceException {
		WalletAccount walletAccount = implement.getUserByUserName(username);
		if(walletAccount == null) {
			logger.info("No user available...");
			throw new WalletAccountServiceException();
		}
		else {
			logger.error("User Details founded...");
			return walletAccount;
		}
	}

	
}

package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.WalletAccount;
import com.cg.entity.WalletMoneyStatement;
import com.cg.exception.CheckDetailsException;
import com.cg.services.WalletAccountDetails;
import com.cg.services.WalletAccountServiceException;
import com.cg.services.WalletServices;

//import javafx.util.Pair;

@SpringBootApplication
@RestController
@CrossOrigin("*")
public class WalletController {
	
	@Autowired
	private WalletServices walletService;
	/*
	 * @Autowired is used to
	 * Inject the dependency of walletService class into walletController class.
	 * It internally uses setter or constructor to inject dependency. 
	 */
	
    
	Logger logger = LoggerFactory.getLogger(WalletController.class);
	/*
	 * Logger used to Record unusual circumstances or error that may be happening in the program.
	 * also use to get info what is going in the application.
	 */
	
	
	
	@PostMapping("/save")
	public ResponseEntity<Object> registerUser(@RequestBody WalletAccount walletAccount) throws Exception {
		
		logger.trace("Save method Accessed...");         // Default level is Info and trace is not upto the Info level so we have to set the property in application.context 
		System.out.println(walletAccount);
	    boolean check = walletService.varifyAccountDetails(walletAccount.getEmailId(), walletAccount.getMobile_no());
	    if(check == false)
	    {
	    	logger.error("MobileNumber/Email already registered...");
	    	throw new RuntimeException("Your MobileNumber/Email Already registered...");
	    }
		try {
			walletService.createAccount(walletAccount);
			logger.info("Wallet Account successfully created...");
			return new ResponseEntity<Object>("Your Wallet Account with UserName: " + walletAccount.getUser_name() + " Created SuccessFully..", HttpStatus.OK);			
		} catch (WalletAccountServiceException exception) {
			throw new WalletAccountServiceException("Internal server Error while connecting with data base");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateUser(@RequestBody WalletAccount walletAccount) throws Exception {
		logger.trace("updateUser method Accessed...");
		try {
		    walletService.updateUser(walletAccount);
		    logger.info("password updated successfully...");
		    return new ResponseEntity<Object>("Password Updated Successfully..!", HttpStatus.OK);
	   }
		catch(WalletAccountServiceException exception) {
			throw new RuntimeException("Internal Server Error while updating data......!");
		}
	}
	
	
	
	@GetMapping("/alldata")
	public ResponseEntity<List<WalletAccountDetails>> getAllAccount() throws WalletAccountServiceException{
		logger.trace("getAllAccount method Accessed...");
		try {
		List<WalletAccountDetails> account = walletService.getAllUser();
		logger.info("all Data Can be acceesed...");
		return new ResponseEntity<List<WalletAccountDetails>>(account, HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception) {
			logger.error("No product is there...");
			throw new RuntimeException("Sorry! Products not available!");
		}
	}
	
	@PostMapping("/varify")
	public ResponseEntity<Object> varifyAccount(@RequestBody UserNamePassword userName) throws Exception {
		logger.trace("varifyAccount method Accessed...");
		String username = userName.username;
		String password = userName.password;
		try { 
		walletService.verifyLoginCredintial(username, password);
		logger.info("Login varified...");
		return new ResponseEntity<Object>(true, HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception) {
			logger.error("userName/password is wrong...");
			throw new Exception("UserName/Password is Wrong...");
		}
		
	}
	
//	@GetMapping("/user")
//	public ResponseEntity<Pair<WalletAccount, List<String>>> varifyLogin(@RequestParam("user") String user_name, @RequestParam("pass") String password) throws Exception {
//		logger.trace("varifyLogin method Accessed...");
//		boolean check = walletService.varifyLoginCredintial(user_name, password);
//		if(check) {
//			List<String> stmt = walletService.printallTransation(user_name, password);
//			WalletAccount wb = walletService.getUser(user_name, password);
//			Pair<WalletAccount, List<String>> hold = new Pair<WalletAccount, List<String>>(wb, stmt);
//			return new ResponseEntity<Pair<WalletAccount, List<String>>>(hold,HttpStatus.OK);
//		}
//		else {
//			throw new RuntimeException("Sorry! no such User available!");
//		}
//	}
	
	@PostMapping("/userOnly")
	public ResponseEntity<Object> getUserdetails(@RequestBody UserNamePassword userName) throws Exception {
		logger.trace("getUserdeatils method Accessed...");
		String username = userName.username;
		String password = userName.password;
		try {
		if(walletService.verifyLoginCredintial(username, password)) {
			  
			   WalletAccount walletAccount = walletService.getUser(username, password);
			   if(walletAccount == null) {
				   logger.error("No user founded...");
				   throw new Exception("No Such user Available...");
			   }
			   logger.info("User founded...");
		       return new ResponseEntity<Object>(walletAccount, HttpStatus.OK);
		}
		else{
			logger.error("UserName/Password is Wrong...");
			throw new Exception("UserName/Password is wrong...");
		  }
		}
		  catch(Exception exception) {
			  throw new Exception("Internal Server Error while getting data...");
		  }
	}
	
	@PostMapping("/userByUser")
	public ResponseEntity<Object> getUserdetailsByUser(@RequestBody String username) {
		logger.trace("getUserdetailsByUser method Accessed...");
		try {
			WalletAccount walletAccount = walletService.getUserByUserName(username);
			logger.info("User is Valid...");
		return new ResponseEntity<Object>(walletAccount , HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception) {
			logger.error("No such user founded...");
			throw new RuntimeException("No Such User Found...!");
		}
	}
	
	@GetMapping("/checkUser")
	public ResponseEntity<Object> CheckUser(@RequestParam("user") String username) throws Exception {
		logger.trace("CheckUser method Accessed..."); 
		try {
		     walletService.checkUserName(username);
		     logger.info("userName Varified...");
			 return new ResponseEntity<Object>(true, HttpStatus.OK);
		 }
		 catch(WalletAccountServiceException exception) {
			 logger.error("UserName already Exist...");
			throw new CheckDetailsException("UserName Already Exist...");
		}

	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Object> DepositBalance(@RequestBody Deposit deposit) throws WalletAccountServiceException {
		logger.trace("DepositBal method Accessed..."); 
		double money = deposit.money;
		String username =  deposit.username;
		String password =  deposit.password;
		try {
		   walletService.depositBalance(money, username, password);
		   logger.info("Money deposited...");
		   return new ResponseEntity<Object>("Money " + money + " credited into your account " + username, HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception){
			logger.error("Transaction cann't be processed...");
			throw new RuntimeException("Maximum amount that Can be deposit in Account is 50000.00");
		}
	}
	
	@PostMapping("/fundTransfer")
	public ResponseEntity<Object> FundTransfer(@RequestBody FundTransfer fund) throws Exception
	{
		logger.trace("FundTransfer method Accessed..."); 
		String user_name = fund.username1;
		String password = fund.password;
		String user_name2 = fund.username2;
		double money = fund.money;
		try {
		walletService.fundTransferAmount(user_name, password, user_name2, money);
		logger.info("Money Transfered successfully...");
		return new ResponseEntity<Object>("Amount " + money + " Transfered from your account to " + user_name2 + " Successfully", HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception) {
			logger.error("Not enough balance...");
			throw new RuntimeException("You Account does Not have Enough balance to tranfer please Recharege Your Wallet.");
		}
	}
	
	@PostMapping("/allTransaction")
	public ResponseEntity<List<WalletMoneyStatement>> allTransaction(@RequestBody UserNamePassword userName) throws Exception{
		logger.trace("FundTransfer method Accessed..."); 
		String username = userName.username;
		String password = userName.password;
		try {
		 List<WalletMoneyStatement> transaction = walletService.printallTransation(username, password);
		 logger.info("Show All Transaction...");
		 return new ResponseEntity<List<WalletMoneyStatement>>(transaction, HttpStatus.OK);	
		}catch(WalletAccountServiceException exception) {
			logger.error("No Transaction is available...");
			throw new RuntimeException("Transaction History is Null...!");
		}
	}
	
//	@GetMapping("/showBal")
//	public ResponseEntity<Object> showBalance(@RequestParam("user") String username, @RequestParam("pass") String password) {
//		logger.trace("showBalance method Accessed..."); 
//		try {
//		double balance = walletService.showBalance(username, password);
//		if(balance == -1) {
//			throw new RuntimeException("Account Does Not Exist...");
//		}
//		else {
//			return new ResponseEntity<Object>("Your Account Balance is " + balance, HttpStatus.OK);
//		}
//		}
//		catch(Exception exception) {
//			throw new RuntimeException("Error While Connecting with Data Base");
//		}
//	}
	
	@PostMapping(value = "/withdraw")
	public ResponseEntity<Object> withdrawAmount(@RequestBody Deposit deposit) throws Exception {
		logger.trace("withdrawAmount method Accessed...");
		double money = deposit.money;
		String username =  deposit.username;
		String password =  deposit.password;
		try {
		    walletService.withdrawAmount(money, username, password);
		    logger.info("Money debited...");
			return new ResponseEntity<Object>("Amount " + money + " Withdraw From Your Account.", HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception){
			logger.error("transaction cann't be processed...");
			throw new RuntimeException("Transaction can not be processed due to 'Insufficient Balance..'");
		}
	}
	
	@GetMapping("/varifyMobile")
	public ResponseEntity<Object> varifyMobile(@RequestParam("mobile") String Mobile) throws Exception {
		logger.trace("varifyMobile method Accessed...");
		try {
		    walletService.varifyMobile(Mobile);
		    logger.info("Mobile Number varified...");
			return new ResponseEntity<Object>(true, HttpStatus.OK);
		}
		catch(WalletAccountServiceException exception) {
			logger.error("Mobile Number is already registered...");
			throw new CheckDetailsException("Mobile Number is Already Exist..");
		}
	}
	
	@GetMapping("/varifyEmail")
	public ResponseEntity<Object> varifyEmail(@RequestParam("email") String email) throws Exception {
		logger.trace("varifyEmail method Accessed...");
		try {
		     walletService.varifyEmail(email);
		     logger.info("Email varified...");
			 return new ResponseEntity<Object>(true, HttpStatus.OK);
		 }
		catch(WalletAccountServiceException exception) {
		 {
			 logger.error("Email is already registered...");
			 throw new CheckDetailsException("Email Already Exist..");
		 }
	}
}
}
 

   // class to receive more then a parameter from angular

class UserNamePassword{
	public String username;
	public String password;
}

class Deposit{
	public String username;
	public String password;
	public double money;
}

class FundTransfer{
	public String username1;
	public String password;
	public String username2;
	public double money;
}
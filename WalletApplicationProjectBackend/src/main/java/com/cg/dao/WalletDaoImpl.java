package com.cg.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.entity.WalletAccount;
import com.cg.entity.WalletMoneyStatement;


/*
 * @Repository annotation is used to indicate that the class provides the mechanism for 
 * storage, retrieval, search, update and delete operation on object.
 */
@Repository("walletDaoImple")

/*
 * @Transactional itself define the scope of a single database transaction.
 */
@Transactional
public class WalletDaoImpl implements WalletDao {

	/*
	 * @PersistenceContext annotation is used to inject EntityManager in session bean. 
	 */
	@PersistenceContext
	private EntityManager entityManager;
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	/*
	 * This method is used to insert a new recored in the table.
	 * @param walletAccount This is parameter of WalletAccount type of createAccount method.
	 * @return boolean this return true if data is successfully inserted into data base otherwise throw exception.
	 */
	@Override
	public boolean createAccount(WalletAccount walletAccount) {
		WalletMoneyStatement walletStatement = new WalletMoneyStatement();
		walletStatement.setDate(dateFormat.format(new Date()));
		
		walletStatement.setStatement("Account Created..");
		walletAccount.addStatements(walletStatement);
        entityManager.persist(walletAccount);
		return true;
	}

	
	/*
	 * This method is used to update record which is already present into database.
	 * @param walletAccount This is parameter of WalletAccount type of updateUser method.
	 * @return boolean this return true if data is successfully updated into data base otherwise throw exception.
	 */
	@Override
	public boolean updateUser(WalletAccount walletAccount) {
		entityManager.merge(walletAccount);
		return true;
	}
	
	
	/*
	 * This method is used to update account balance when money is created into your account.
	 * @param money this is first parameter of depositBalance method which is of double type.
	 * @param username this is second parameter of depositeBalance method which is of String type.
	 * @param password this is third parameter of deposieBalance method which is of String type.
	 * @return boolean this return true if balance successfully deposited otherwise throw exception.
	 */
	@Override
	public boolean depositBalance(double money, String username, String password) {
		WalletAccount walletAccount = entityManager.find(WalletAccount.class, username);
		walletAccount.setBalance(walletAccount.getBalance()+money);
		WalletMoneyStatement walletMoney = new WalletMoneyStatement();
		walletMoney.setDate(dateFormat.format(new Date()));
		walletMoney.setActionType(1);
		walletMoney.setAction("+ " + money);
		walletMoney.setStatement("Money Deposit Rs. " + money + " in your account updated balance: " + (walletAccount.getBalance()));
		walletAccount.addStatements(walletMoney);
	    entityManager.merge(walletAccount);
		return true;
	}

	
	/*
	 * This method is used to transfer money from one account to another account(fund Transfer).
	 * @param username1 this is first parameter of fundTransferAmount method which is of String type.
	 * @param password this is second parameter of fundTransferAmount method which is of String type.
	 * @param username2 this is third parameter of fundTransferAmount method which is of String type.
	 * @param balance this is fourth parameter of fundTransferAmount method which is of double type.
	 * @return boolean this return true if balance successfully deposited from your account and credited into other account otherwise throw exception.
	 */
	@Override
	public boolean foundTransferAmount(String username1, String password, String username2, double balance) {
		WalletAccount walletAccount1 = entityManager.find(WalletAccount.class, username1);
		WalletAccount walletAccount2 = entityManager.find(WalletAccount.class, username2);
		double currentBalance = walletAccount1.getBalance();
		if(currentBalance < balance) {
			return false;
		}
		else
		{
			walletAccount1.setBalance(walletAccount1.getBalance() - balance);
			walletAccount2.setBalance(walletAccount2.getBalance() + balance);
            WalletMoneyStatement ms1 = new WalletMoneyStatement();
            WalletMoneyStatement ms2 = new WalletMoneyStatement();
            ms1.setDate(dateFormat.format(new Date()));
            ms2.setDate(dateFormat.format(new Date()));
            ms1.setActionType(-1);
            ms1.setAction("- " + balance);
            ms1.setStatement("Money Withdraw Rs. " + balance + " from your account to another account " + username2 + " updated balance: " + (currentBalance - balance));
            
            ms2.setActionType(1);
            ms2.setAction("+ " + balance);
            ms2.setStatement("Money cradited Rs. " + balance + " from account " + username1 + " to your account updated balance: " + walletAccount2.getBalance());
            walletAccount1.addStatements(ms1);
            walletAccount2.addStatements(ms2);
            entityManager.merge(walletAccount1);
            entityManager.merge(walletAccount2);
            return true;	
		}
	}

	
	/*
	 * This method is used to Show all transaction of a specific user.
	 * @param username this is first parameter of printallTransation method which is of String type.
	 * @param password this is second parameter of printallTransation method which is of String type.
	 * @return List<WalletMoneyStatement> this return all transaction made by a given account. it return the list of object which contain some more information.
	 */
	@Override
	public List<WalletMoneyStatement> printallTransation(String username, String password) {
		String statement = "SELECT state FROM WalletMoneyStatement state WHERE user_name=:pUser ORDER BY state.transaction_id desc";
		TypedQuery<WalletMoneyStatement> query = entityManager.createQuery(statement, WalletMoneyStatement.class);
		query.setParameter("pUser", username);
		return query.getResultList();
	}

	
	/*
	 * This method is used to show Current account balance of specific user.
	 * @param user_name this is first parameter of showBalance method which is of String type.
	 * @param password this is second parameter of showBalance method which is of String type.
	 * @return double this return current account balance in your account.
	 */
	@Override
	public double showBalance(String user_name, String password) {
		WalletAccount ws = entityManager.find(WalletAccount.class, user_name);
		return ws.getBalance();
	}

	
	/*
	 * This method is used to withdraw money from a given account details.
	 * @param money this is first parameter of withdrawAmount method which is of Double type.
	 * @param username this is second parameter of withdrawAmount method which is of String type.
	 * @param password this is third parameter of withdrawAmount method which is of String type.
	 * @return boolean this return true if money successfully withdraw from your account otherwise return false.
	 */
	@Override
	public boolean withdrawAmount(Double money, String username, String password) {
		WalletAccount walletAccount = entityManager.find(WalletAccount.class, username);
        double balance = walletAccount.getBalance();
        if(balance < money) {
        	return false;
        }
        else
        {
        	walletAccount.setBalance(walletAccount.getBalance() - money);
        	WalletMoneyStatement walletMoney = new WalletMoneyStatement();
        	walletMoney.setActionType(-1);
        	walletMoney.setAction("- " + money);
        	walletMoney.setDate(dateFormat.format(new Date()));
        	walletMoney.setStatement("Money Withdraw Rs. \t\t" + money + " in your account updated balance: \t" + (walletAccount.getBalance()));
        	walletAccount.addStatements(walletMoney);
        	entityManager.merge(walletAccount);
            return true;
        }
	}
	
	
	/*
	 * This method is used to verify Login Credentials details of user.
	 * @param username this is first parameter to verifyLoginCredintial method which is of String type.
	 * @param password this is second parameter to verifyLoginCredintial method which is of String type.
	 * @return boolean this return true if details are verified otherwise return false.
	 */
	public boolean verifyLoginCredintial(String username, String password) {
		WalletAccount walletAccount = entityManager.find(WalletAccount.class, username);
		
		if(walletAccount == null) {
			return false;
		}
		if(walletAccount.getPassword().equals(password)) {
			return true;
		}
		else
			return false;
	}
	
	
	/*
	 * This method is used to check whether userName is already present in database or not.
	 * @param username this is first parameter to checkUserName method which is of String type.
	 * @return boolean this return false when user is not there in data base otherwise return false.
	 */
	public boolean checkUserName(String username) {
		WalletAccount walletAccount = entityManager.find(WalletAccount.class, username.toLowerCase());
		if(walletAccount == null) {
			return false;
		}
		else
			return true;
	}
	
	
	/*
	 * This method is used to get details of all user present in database.
	 * @return List<WalletAccount> this return all users details as an object form if present otherwise return null value.
	 */
   @Override	
   public List<WalletAccount> getAllUser(){
	   String statement = "SELECT user FROM WalletAccount user";
	   TypedQuery<WalletAccount> query = entityManager.createQuery(statement, WalletAccount.class);
	   return query.getResultList();
	   
   }

   
    /*
	 * This method is used to get details of a specific user.
	 * @param username this is first parameter of getUser method which is of String type.
	 * @param password this is second parameter of getUser method which is of string type.
	 * @return WalletAccount this return detail of a specific user in form of an object otherwise return null value.
	 */
   @Override
   public WalletAccount getUser(String username, String password) {
	  List<WalletAccount> allUsers = getAllUser();
	  List<WalletAccount> accountDeatils = allUsers.stream().filter(e->e.getUser_name().equals(username) && e.getPassword().equals(password)).collect(Collectors.toList());
	  return accountDeatils.get(0);		  
   }
   
   
    /*
	 * This method is used to get details of a specific user.
	 * @param username this is first parameter of getUserByUserName method which is of String type.
	 * @return WalletAccount this return detail of a specific user in form of an object otherwise return null value.
	 */
   @Override
   public WalletAccount getUserByUserName(String username) {
	   List<WalletAccount> users = getAllUser();
	   List<WalletAccount> walletAccount = users.stream().filter(e->e.getUser_name().equals(username)).collect(Collectors.toList());
	   if(walletAccount.size() == 0)
		   return null;
	   else
	   return walletAccount.get(0);	
   }
}

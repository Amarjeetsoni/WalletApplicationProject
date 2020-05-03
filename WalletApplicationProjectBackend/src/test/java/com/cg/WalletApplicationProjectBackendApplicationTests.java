package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.controller.WalletController;
import com.cg.services.WalletAccountServiceException;
import com.cg.services.WalletServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WalletApplicationProjectBackendApplicationTests {

	
	/*
	 * Logger used to Record unusual circumstances or error that may be happening in the program.
	 * also use to get info what is going in the application.
	 */
	Logger logger = LoggerFactory.getLogger(WalletController.class);
	
	
	/*
	 * @Autowired is used to
	 * Inject the dependency of walletService class into WalletApplicationProjectBackendApplicationTests class.
	 * It internally uses setter or constructor to inject dependency.  
	 */
	@Autowired
	private WalletServices walletService;
	
	
	/*
	 * @BeforeAll annotation is used to signal that this method should be executed before any test case runs only once.
	 */
	@BeforeAll
	static void setUpBeforeClass() {
		System.out.println("Feating resouce Fetching resources for testing ...");
	}
	
	/*
	 *  @BeforeEach annotation is used to signal that this method should be executed before all test cases.
	 */
	@BeforeEach
	void setup() {
		System.out.println("Test Case Started");
	}
	
	
	/*
	 *  @AfterEach annotation is used to signal that this method should be executed after all test cases.
	 */
	@AfterEach
	void tearDown() {
		System.out.println("Test Case Over");
	}
	
	
    /*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to verify login credentials. 
     */
 	@Test
	@DisplayName("User Login Successfully...")
	void loginTest() throws Exception {
		
		logger.info("Validation for Valid Login Credentials...");
		boolean check = false;
		String message = null;
		
		//--------------------- TEST CASE 1 -----------------------------//
		/*
		 * In this test case we are passing login details as null and the Exception that are return by 
		 * that function is "Null value is not allowed...".
		 */
		
		try {
			check =  walletService.verifyLoginCredintial(null, "AMar@1808");
		}
		catch(WalletAccountServiceException e) {
			message = e.getMessage();
		}
		assertEquals("Null value is not allowed...", message);
		
		
		// --------------------- TEST CASE 2 --------------------------//
		/*
		 * In this test case we are passing login details correct and the function will return 
		 * true as a result.
		 */
		try {
			check =  walletService.verifyLoginCredintial("amasoni", "AMar@1808");
		}
		catch(Exception e) {
			message = e.getMessage();
		}
		assertEquals(true, check);
		
		
		// ---------------------- TEST CASE 3 -------------------------//
		/*
		 * In this test case we are passing login details but userName/password is wrong so function
		 * will return an exception message that is "Not a valid credential...".
		 */
		try {
			check =  walletService.verifyLoginCredintial("amasoni", "amar");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Not a valid credential...", message);
		
		
		// ---------------------- TEST CASE 4 -------------------------//
		/*
		 * In this test case we are passing login details with an empty string and function will again throw 
		 * an exception with a message "Null value is not allowed...".
		 */
		try {
			check =  walletService.verifyLoginCredintial("amasoni", "");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Null value is not allowed...", message);
		
	}
	
	
	
 	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to Test deposit amount into your account. 
     */
	@Test
	@DisplayName("Money deposit Successfully...")
	void depositTest() throws Exception {
		logger.info("Deposit Money Validation...");
		boolean check = false;
		String message = null;
		
		//-------------------------- TEST CASE 1---------------------------------//
		/*
		 * In this test case we are passing amount with userName and password but the login credential is 
		 * wrong so this function return message as "Not a valid credential...".
		 */
		try {
			check = walletService.depositBalance(100, "amasoni", "AMar@180");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals("Not a valid credential...", message);
		
		
		
		//-------------------------- TEST CASE 2 ---------------------------------//
		/*
		 * In this test case we are passing amount with userName and password but the userName is 
		 * null so this function return message as "Null value is not allowed...".
		 */
		try {
			 check = walletService.depositBalance(100, null, "AMar@180");
		}
		catch(WalletAccountServiceException exception) {
			 message = exception.getMessage();
		}
				
		assertEquals("Null value is not allowed...", message);
		
		
		//-------------------------- TEST CASE 3 ---------------------------------//
		/*
		 * In this test case we are passing amount more then 50000 with correct login credentials
		 * then it will return as error message "Money Should Not be Greater then 50000...".
		 */
		try {
			check = walletService.depositBalance(60000, "amasoni", "AMar@1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals("Money Should Not be Greater then 50000 or less then 0...", message);
		
		
		
		//-------------------------- TEST CASE 4 ---------------------------------//
		/*
		 * In this test case we are passing correct userName and password with valid amount to deposit and 
		 * the function return true as an output.
		 */
		try {
			check = walletService.depositBalance(200, "amasoni", "AMar@1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals(true, check);	
	}
	
	
	
	
	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to Test fund transfer details. 
     */
	@Test
	@DisplayName("fund transfered Successfully...")
	void fundTransferTest() throws Exception {
		logger.info("Fund Transfer validation...");
		boolean check = false;
		String message = null;
		
		//------------------------------ TEST CASE 1 ------------------------------------//
		/*
		 * In this function we are passing 4 argument first one is userName of sender and second one is password of sender and 
		 * third one is userName of receiver and fourth one is amount so, in this test case useName of receiver is not valid so 
		 * function will return "UserName Does not Valid...!" as an error message.
		 */
		try {
			check = walletService.fundTransferAmount("amasoni", "AMar@1808", "amar123", 250);
		}
		catch(Exception exception) {
			message = exception.getMessage();
		}
		assertEquals("UserName Does not Valid...!", message);
		
		
		//------------------------------- TEST CASE 2 ------------------------------------//
		/*
		 * In this test case we are passing the receiver userName same as sender userName then the function
		 * throw an exception with the message "Username must be different than yours..";
		 */
		
		try {
			check = walletService.fundTransferAmount("amasoni", "AMar@1808", "amasoni", 250);
		}
		catch(Exception exception) {
			message = exception.getMessage();
		}
		assertEquals("Username must be diffrent than yours..", message);
		
		//-------------------------------- TEST CASE 3 ----------------------------------//
		/*
		 * In this test case we are passing wrong login credentials of sender and function will throw an exception with
		 * the message  "Not a valid credential...".
		 */
		try {
			check = walletService.fundTransferAmount("amasoni", "AMar@180", "amarya", 250);
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Not a valid credential...", message);
		
		
		//------------------------------- TEST CASE 4 -----------------------------------//
		/*
		 * In this test case we are passing amount more then present in that account and other then that every thing is correct
		 * and then function will throw an exception with the message "Your Account Balance is Low...".
		 */
		try {
			check = walletService.fundTransferAmount("amasoni", "AMar@1808", "amarya", 25000);
		} 
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Your Account Balance is Low...", message);
		
		
		
		//------------------------------ TEST CASE 5 -------------------------------------//
		
		/*
		 * In this test case we are passing every thing fine and the function will return 
		 * true as an output so, by this we can verify amount is successfully transfered into receiver account. 
		 */
		try {
			check = walletService.fundTransferAmount("amasoni", "AMar@1808", "amarya", 25);
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals(true, check);
		
		//------------------------------- TEST CASE 6 -----------------------------------//
		/*
		 * In this test case we are passing account as null and amount as negative values and the function will throw
		 * exception with the message "Null Values/ balance less then or zero not allowed...".
		 */
		try {
			check = walletService.fundTransferAmount(null , "AMar@1808", "amarya", -25);
		}
		catch(Exception exception) {
			message = exception.getMessage();
		}
		assertEquals("Null Values/ balance less then or zero not allowed...", message);
		
	}
	
	
	
	
	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to check functionality of withdraw. 
     */
	@Test
	@DisplayName("Money Withdraw Successfully...")
	void withdrawTest() throws Exception {
		logger.info("Withdraw Amount Validation...");
		boolean check = false;
		String message = null;
		
		//--------------------------- TEST CASE 1 -------------------------------//
		/*
		 * In this function we are passing three arguments first one is amount second one is userName and
		 * third one is password, so in this test case we are passing wrong details so the output that 
		 * function will return is an exception with the message "Not a valid credential...". 
		 */
		try {
			check = walletService.withdrawAmount(100.0, "amasoni", "AMar@180");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals("Not a valid credential...", message);
		
		
		//---------------------------- TEST CASE 2 -----------------------------//
		/*
		 * In this test case we are passing negative amount so the function is expected to throw an
		 * exception which will show as "Money Should be greater then 0...!".
		 */
		try {
			check = walletService.withdrawAmount(-50.0, "amasoni", "AMar@1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals("Money Should be greater then 0...!", message);
		
		
		//---------------------------- TEST CASE 3 ------------------------------//
		/*
		 * In this test case we are passing every parameter as valid and we will get the 
		 * true as output which shows that the amount is successfully debited from your account.
		 */
		try {
			check = walletService.withdrawAmount(5.0, "amasoni", "AMar@1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals(true, check);	
		
		
		
		//----------------------------- TEST CASE 4 ---------------------------------//
		/*
		 * In this test case we are passing every parameter as valid but we are passing amount 
		 * which is greater then account balance so function is expected to throw an exception 
		 * with the message "Not Enough balance In Your account...". 
		 */
		try {
			check = walletService.withdrawAmount(500000.0, "amasoni", "AMar@1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		
		assertEquals("Not Enough balance In Your account...", message);	
		
		
	}
	
	
	
	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to check whether the userName is already present in the database or Not. 
     */
	@Test
	@DisplayName("User Available...")
	@Rollback(true)
	void checkUserNameTest() throws Exception {
		logger.info("check username Validation...");
		boolean check = false;
		String message = null;
		
		
		//------------------------- TEST CASE 1 -------------------------------------//
		/*
		 * In this function only argument one argument is present which indicate userName, so we pass the 
		 * userName and it return us as message, in this test case the userName is already exist,
		 * so the function throw an exception with message "userName Already exist Try for another!!...".
		 */
		try {
			check = walletService.checkUserName("amasoni");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("userName Already exist Try for another!!...", message);
		
		
		//-------------------------- TEST CASE 2 -----------------------------------//
		/*
		 * In this test case we are passing new userName and the function returns as false which will show
		 * that this userName is not present in the data base.
		 */
		try {
			check = walletService.checkUserName("amasoni1808");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals(false, check);
		
		
		//-------------------------- TEST CASE 3 -----------------------------------//
		/*
		 * In this test case we are passing userName as null and the function is expected to throw an 
		 * exception with the message "null values are not allowed...".
		 */
		try {
			check = walletService.checkUserName(null);
		}
		catch(Exception exception) {
			message = exception.getMessage();
		}
		assertEquals("null values are not allowed...", message);
		
	}
	
	
	
	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to check whether the EmailId is already present in the database or Not. 
     */
	@Test
	@DisplayName("Email validation...")
	@Rollback(true)
	void varifyEmailTest() throws Exception {
		logger.info("varifyEmail Validation...");
		boolean check = false;
		String message = null;
		
		//------------------------ TEST CASE 1 --------------------------------------// 
		/*
		 * In this function only argument one argument is present which indicate EmailId, so we pass the 
		 * EmailId and it return us a message, in this test case the Email is already exist,
		 * so the function throw an exception with message "Email Already Registered...".
		 */
		try {
			check = walletService.varifyEmail("amarjeet.soni@capgemini.com");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Email Already Registered...", message);
		
		
		
		//------------------------ TEST CASE 2 -------------------------------------//
		/*
		 * In this test case we are passing new EmailId and the function returns as true which will show
		 * that this Email is not present in the data base.
		 */
		try {
			check = walletService.varifyEmail("amarjeet@gmail.com1");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals(true, check);
		
	}
	
	
	
	/*
     * @Test denotes that this method should be run as test case.
     * @DisplayName used to display custom name of test cases.	
     * This function is used to check whether Mobile number is already present or not in the database. 
     */
	@Test
	@DisplayName("Mobile Number validation...")
	@Rollback(true)
	void varifyMobileTest() throws Exception {
		System.out.println("varifyMobile Validation...");
		boolean check = false;
		String message = null;
		
		//------------------------- TEST CASE 1 ------------------------------------//
		/*
		 * In this function only argument one argument is present which indicate Mobile number, so we pass the 
		 * Mobile Number and it return us a message, in this test case the Mobile number is already exist,
		 * so the function throw an exception with message "Mobile number Already Registered...".
		 */
		try {
			check = walletService.varifyMobile("8005168551");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals("Mobile number Already Registered...", message);
		
		
		
		//--------------------------- TEST CASE 2 --------------------------------//
		/*
		 * In this test case we are passing new MobileNumber and the function returns as true which will show
		 * that this MobileNumber is not present in the data base.
		 */
		try {
			check = walletService.varifyMobile("1569874632");
		}
		catch(WalletAccountServiceException exception) {
			message = exception.getMessage();
		}
		assertEquals(true, check);
	}
	
	
	
	

}

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { WalletAccount } from '../model/WalletAccount';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user_name: string;           // instance variable used to store the username of a perticular user.
  password: string;            // instance varibale used to store the password.
  user_name2: string;
  constructor(private http: HttpClient) { }

  /**
   * checkLogin method used to varify login details of user and returns true or an error message.
   * @param user first parameter of checkLogin method of string type contains username. 
   * @param pass second parameter of checkLogin method of string type contains password.
   */
  checkLogin(username, password) {
    this.user_name = username;
    this.password = password;
    return this.http.post("http://localhost:8080/varify", { "username": this.user_name, "password": this.password }).pipe(
      catchError(this.handleError));
  }

  /**
   * getUser method used to get the complete information of user from backend database.
   * if detail is available then it will return WalletAccount class object,
   * otherwise it will throw an exception.
   */
  getUser() {
    return this.http.post<WalletAccount>("http://localhost:8080/userOnly", { "username": this.user_name, "password": this.password }).pipe(
      catchError(this.handleError));
  }


  /**
   * depositMoney method used to deposit amount into perticular account.
   * @param money only parameter of depositMoney method of double type.
   * return true if amount successfully deposited otherwise throw an exception.
   */
  depositMoney(money) {
    return this.http.post("http://localhost:8080/deposit", { "username": this.user_name, "password": this.password, "money": money }, { responseType: 'text' as 'json' }).pipe(
      catchError(this.handleError));
  }


  /**
   * withdrawMoney imethod used to withdraw amount into perticular account.
   * @param money only parameter of withdrawMoney method of double type.
   * funtion will return true if money is withdraw successfully otherwise it will throw some exception.
   */
  withdrawMoney(money) {
    return this.http.post("http://localhost:8080/withdraw", { "username": this.user_name, "password": this.password, "money": money }, { responseType: 'text' as 'json' }).pipe(
      catchError(this.handleError));
  }


  /**
   * checkAccount method used to check weather the username is exist in data base or not.
   * @param user only parameter of checkAccount method of string type.
   * return false if user does not exist otherwise throw excpetion.
   */
  checkAccount(user) {
    this.user_name2 = user;
    return this.http.get("http://localhost:8080/checkUser?user=" + user).pipe(
      catchError(this.handleError));
  }


  /**
   * getUserByUserName method used to get users details by only username.
   * @param user only parameter of getUserByUserName of string type.
   * if details are correct then it returns object of WalletAccount class otherwise throws some exception.
   */
  getUserByUserName(user) {
    return this.http.post<WalletAccount>("http://localhost:8080/userByUser", user);
  }


  /**
   * fundTransfer method used to transfer money from your account to another account.
   * @param money first parameter of fundTransfer method of double type.
   * @param user_name2 second parameter of fundTransfer method of string type.
   * return true if money transfered otherwise throws exception. 
   */
  fundTransfer(money, user_name2) {
    return this.http.post("http://localhost:8080/fundTransfer", { "username1": this.user_name, "password": this.password, "username2": user_name2, "money": money }, { responseType: 'text' as 'json' });
  }


  /**
   * printAllTransaction method used to get details of all transaction made by periticulat account.
   * if no transaction is there then function throw an excption.
   */
  printAllTransaction() {
    return this.http.post<string[]>("http://localhost:8080/allTransaction", { "username": this.user_name, "password": this.password });
  }



  /**
   *addUser method is used to add new user details into the database. 
   * @param data only parameter of addUser method of WalletAccount type.
   * returns true if successfully added record otherwise throws an exception.
   */
  addUser(data) {
    this.user_name = data.user_name;
    this.password = data.password;
    return this.http.post("http://localhost:8080/save", data, { responseType: 'text' as 'json' });
  }


  /**
   * UpadteUser method used to update the password of user.
   * @param data only parameter of UpdateUser method of WalletAccount type.
   * returns true if details updated successfully otherwise throws an excpetion.
   */
  UpdateUser(data) {
    return this.http.put("http://localhost:8080/update", data, { responseType: 'text' as 'json' });
  }


  /**
   * checkMobile method used to check mobile number availablity in data base.
   * @param mobile only parameter of checkMobile which is of string type.
   * returns true if mobile number is already there otherwise throws an excpetion.
   */
  checkMobile(mobile) {
    return this.http.get("http://localhost:8080/varifyMobile?mobile=" + mobile);
  }


  /**
   * checkEmail method used to check Email id availablity in data base.
   * @param email only parameter of checkEmail which is of string type.
   * returns true if Email Id is already there otherwise throws an excpetion.
   */
  checkEmail(email) {
    return this.http.get("http://localhost:8080/varifyEmail?email=" + email);
  }


  /**
   * getAllUser is a method used to get details of all user.
   * if No user is available then throw a exception.
   */
  getAllUser() {
    return this.http.get<WalletAccount[]>("http://localhost:8080/alldata").pipe(
      catchError(this.handleError));
  }


  /**
   * handleError method used to handle the exception conditions that are thrown from backend.
   * @param errorResponse only parameter of handleError method of HttpErrorResponse type.
   */
  private handleError(errorResponse: HttpErrorResponse) {
    return throwError(errorResponse);
  }
}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor() { }

  /*
    authenticate method runs after a user provide correct login credentials.
    it will store the username in the session storage.
    the value of username is remain in session untill user logged out or browser closed.
  */
  authenticate(username, password) {
    sessionStorage.setItem('username', username);
  }


  /* 
  isWserLoggedIn method helps up to provide the information about the session storage.
  it shows that currently any user is logged in or not by just checking the value of session storage.
  */
  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    return !(user == null);
  }

  /* 
   logOut method is used to remove the session storage value.
   after exicution of logOut funtion session of that perticular person is ended.
  */
  logOut() {
    sessionStorage.removeItem('username');
  }
}

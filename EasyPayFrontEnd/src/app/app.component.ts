import { Component } from '@angular/core';
import { Router } from '@angular/router'
import { AuthenticationService } from './services/authentication.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'EASYPAY Wallet Application';                  // instance variable
  /* 
     todaysDate of Date type variable which will take system date as value.
  */
  todaysDate = new Date();
  constructor(private router: Router, private auth: AuthenticationService) {
    /*
       setInterval function exicute every 1000 mili sec(1 sec). In setInterval function we update the 
       value of variable todaysDate which will display like running clock on web page.
    */
    setInterval(() => {
      this.todaysDate = new Date();
    }, 1000);
  }



}


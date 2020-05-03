import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-print',
  templateUrl: './print.component.html',
  styleUrls: ['./print.component.css']
})
export class PrintComponent implements OnInit {

  /* instance varible to store the details of transaction that are comming form spring REST application. */
  getValue: boolean = true;
  user_name: string;
  transaction: any[];

  // Injecting all relevant services inside the constructor
  constructor(private service: UserService, private auth: AuthenticationService, private router: Router) {

    if (this.service.user_name == null) {
      this.logOutUser();
    }

    /*printAllTransaction method is used to get data from backend and the 
    data is all the transaction that are made by that perticuar account. */
    this.service.printAllTransaction().subscribe((data) => {
      /* if any one transaction made by that perticular user then this part of code will exicute. */
      this.transaction = data;
      console.log(this.transaction);
      this.getValue = false;
    },
      (err) => {
        /* other wise in a popup the message shown that are comes form spring REST. and control is redirected to home page. */
        alert(err.error);
        this.router.navigate(['/home']);
      })
  }

  ngOnInit() {
    this.user_name = this.service.user_name;
  }

  /*logOutUser method is called to logout the user and redirect control to login page. */
  logOutUser() {

    this.auth.logOut();
    this.router.navigate(['/login']);
  }
}

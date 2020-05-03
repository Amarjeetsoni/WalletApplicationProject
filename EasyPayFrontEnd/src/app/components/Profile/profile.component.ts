import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  /* Store all the details of profile component into instance variables */
  wallet: WalletAccount;
  gender: string;
  mobile_no: string;
  emailId: string;
  user_name: string;
  dob: string;
  submitted: boolean = false;
  isMale: boolean = false;
  isFemale: boolean = false;
  name: string;
  constructor(private service: UserService, private router: Router, private auth: AuthenticationService) {
    /* if session storage username is empty then user is automatically redirected to login page */
    if (this.service.user_name == null) {
      this.logOutUser();
    }
    /* getUser method is called to get the detials of the user from backend database */
    this.service.getUser().subscribe((data) => {
      /* data part is exicuted when spring REST returns object otherwise throw some exception */
      this.wallet = data;
      this.gender = this.wallet.gender;
      this.mobile_no = this.wallet.mobile_no;
      this.emailId = this.wallet.emailId;
      this.user_name = this.wallet.user_name;
      this.dob = this.wallet.dob;
      /* making the full name by combining firstname, middle name and last name.*/
      if (this.wallet.middleName != null) {
        this.name = this.wallet.firstName + " " + this.wallet.middleName + " " + this.wallet.lastName;
      }
      else {
        this.name = this.wallet.firstName + " " + this.wallet.lastName;
      }
      /* According to gender the male and female photo is displayed so according to that we store details of
      that into the some values. */
      if (this.gender == "male") {
        this.isMale = true;
      }
      if (this.gender == "female") {
        this.isFemale = true;
      }
    },
      (err) => {
        /* If any kind of excpetion occures then it will exicute this part of code. */
        alert(err.error);
      });
  }

  ngOnInit() {
  }

  /* this is called to logout users session. */
  logOutUser() {
    this.auth.logOut();
    this.router.navigate(['/login']);
  }
}

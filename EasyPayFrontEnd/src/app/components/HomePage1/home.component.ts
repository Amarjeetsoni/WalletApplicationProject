import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  /*instance varible to handle all kind of details that are comes form the backend data base. */
  name: string;
  userName: string;
  password: string;
  todaysDate = new Date();
  rot: boolean = false;
  currentBal: number = 0;
  wallet: WalletAccount;
  firstName: string;
  middleName: string;
  lastName: string;
  gender: string;
  mobile_no: string;
  emailId: string;
  user_name: string;
  dob: string;
  isMale: boolean = false;
  isFemale: boolean = false;

  // Injecting all relevant services inside the constructor
  constructor(private router: Router, private route: ActivatedRoute, private service: UserService, private auth: AuthenticationService) {
    /* before geting into this function control has to get the object
    of login user. getUser method is used to get all information of user.*/
    this.service.getUser().subscribe((data) => {
      /* get and store all the details of user */
      this.wallet = data;
      this.currentBal = this.wallet.balance;
      /* after getting the information make the full name by 
      using first name, middle name and last name */

      if (this.wallet.middleName != null) {
        this.name = this.wallet.firstName + " " + this.wallet.middleName + " " + this.wallet.lastName;
      }
      else {
        this.name = this.wallet.firstName + " " + this.wallet.lastName;
      }

      this.userName = this.wallet.user_name;
      this.password = this.wallet.password;
      this.firstName = this.wallet.firstName;
      this.middleName = this.wallet.middleName;
      this.lastName = this.wallet.lastName;
      this.gender = this.wallet.gender;
      this.mobile_no = this.wallet.mobile_no;
      this.emailId = this.wallet.emailId;
      this.user_name = this.wallet.user_name;
      this.dob = this.wallet.dob;
      if (this.gender == "male") {
        this.isMale = true;
      }
      if (this.gender == "female") {
        this.isFemale = true;
      }
    },
      (err) => {
        /*if any kind of error get from backend then that is handled by this part. */

        alert(err.error);

        /* it rediected the controller to login page. */
        this.logOutUser();
      });
  }
  /* at the starting if the function it will check the status of user. */
  ngOnInit() {
    if (this.auth.isUserLoggedIn() == false) {
      this.router.navigate(['/login']);
    }
  }

  /* this is called to logout users session. */
  logOutUser() {
    this.auth.logOut();
    this.router.navigate(['/login']);
  }
  rotate() {
    this.rot = true;
  }

}

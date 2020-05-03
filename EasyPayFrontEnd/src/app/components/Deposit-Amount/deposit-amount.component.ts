import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-deposit-amount',
  templateUrl: './deposit-amount.component.html',
  styleUrls: ['./deposit-amount.component.css']
})
export class DepositAmountComponent implements OnInit {
  // Injecting all relevant services inside the constructor
  constructor(private formbuilder: FormBuilder, private router: Router, private service: UserService, private auth: AuthenticationService) {
    // checking is userlogged in or not.
    if (this.service.user_name == null) {
      this.logOutUser();
    }
  }
  // instance variable to store the values and handle the UI part of this component.
  addForm: FormGroup;
  submitted: boolean = false;
  amount: any;
  password: any;
  ErrorAmount: boolean = false;
  WrongPassword: boolean = false;
  processing: boolean = false;
  user_name: string;
  ngOnInit() {
    /* Reactive form component to take the input from the user. */
    this.user_name = this.service.user_name;
    this.addForm = this.formbuilder.group({
      Amount: ['', [Validators.required, Validators.min(1)]],
      password: ['', Validators.required]
    });

  }

  /*
   depositAmount method call once you hit the depost button on UI part. 
   It will validate all the condition of the form and pass all the details to 
   spring rest backend database and according to return response from backend 
   it will show the response on UI.
  */
  depositAmount() {
    this.submitted = true;
    this.WrongPassword = false;
    /* validate the addForm and validation is success then only 
      it allows to exicute futher line of code. */
    if (this.addForm.invalid) {
      return;
    }
    let money = this.addForm.controls.Amount.value;

    this.password = this.addForm.controls.password.value;
    /* matching the password with data base and the password that user had enterd 
    if both are same then user is allowed to deposit that money into your account. */
    if (this.password == this.service.password) {

      // before getting further it it will take the conformation form the user.
      if (confirm(`Do You Want to Add Amount ${money} in your Account...`)) {
        this.processing = true;
        /* depositMoney methid take the amount and pass the amount and username and password to bakeend 
        and wait the response that are comming form backend. */
        this.service.depositMoney(money).subscribe((data) => {
          /* if every thing is fine then in an alert it will show an message
          which comes from backeend data base. */
          alert(data);
          /* after depositing amount user will redirected to the home page. */
          this.router.navigate(['/home']);
        },
          (err) => {
            /* if any kind of error occurs then this will catch error and process that error. */
            alert(err.error);
            /* ErrorAmount and processing are variable which handle the UI part of this component. */
            this.ErrorAmount = true;
            this.processing = false;
          })
      }
    }
    else {
      this.WrongPassword = true;
    }


  }
  /* logOutUser method used to signout the current user and remove the session details
  form the local storage. */
  logOutUser() {
    this.auth.logOut();
    this.router.navigate(['/login']);
  }
}

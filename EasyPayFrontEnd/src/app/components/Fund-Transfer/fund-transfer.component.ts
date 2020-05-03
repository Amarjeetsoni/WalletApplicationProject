import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-fund-transfer',
  templateUrl: './fund-transfer.component.html',
  styleUrls: ['./fund-transfer.component.css']
})

export class FundTransferComponent implements OnInit {

  /* variable are used to handle UI part of this component. */
  username: string;
  searchText: any;
  allUser: WalletAccount[];
  noUser: boolean = false;
  // Injecting all relevant services inside the constructor
  constructor(private formbuilder: FormBuilder, private router: Router, private services: UserService, private auth: AuthenticationService) {

    /* check the username if it is in session storage then you can access the page otherwise
    the controller will automatically redirected to login page. */
    if (this.services.user_name == null) {
      this.logOutUser();
    }

    /* getUser is helps us to get the details of current user form the data base. */
    this.services.getUser().subscribe((data) => {
      /* if username and password is correct and user exist then this part of code 
      works */
      this.wallet = data;
      this.balance = this.wallet.balance;
      this.username = this.wallet.user_name;
    },
      (err) => {
        /*otherwise this part of code works. */
        console.log(err.error);
      })

    /* getAllUser gives the details of all user that are present in the data base. */
    this.services.getAllUser().subscribe((data) => {
      /* if the details of user present in the data base then this block of code
      will exicute and get the details from backend. */
      this.allUser = data;
      this.Loading = true;
    },
      (err) => {
        /* otherwise exception occures and user will get error message as an alert. */
        this.noUser = true;
        this.message = err.error;
        this.Loading = true;
        // above variable are used to handle UI part of this component.
        alert(err.error);
      })
  }
  /* these are again the instance local variable which is used to handle the
  UI part of thus component. */

  wallet: WalletAccount;
  addForm: FormGroup;
  balance: number = 0;
  submitted: boolean = false;
  checkBalance: boolean = false;
  message: any;
  password: any;
  WrongpassWord: boolean = false;
  processing: boolean = false;
  UserNameValidation: boolean = false;
  Loading: boolean = false;

  /* this function exicute when control comes on this component, so we are using this
  component to intialize an form data.  */
  ngOnInit() {
    /* angular reactive form with the required validations. */
    this.addForm = this.formbuilder.group({
      user_name: ['', [Validators.required]],
      Amount: ['', [Validators.required, Validators.min(1)]],
      mobile_no: ['', [Validators.required]],
      password: ['', Validators.required]
    });

  }

  /* fundTransfer method is called to transfer the amount form your account to another
  user who has the existing user of EASYPAY wallet.  */
  fundTransfer() {
    this.submitted = true;
    this.WrongpassWord = false;
    this.checkBalance = false;
    this.UserNameValidation = false;
    /* checks the validation of the reactive form if vaidation is 
    fullfilled then only it allows controller to go through the further code. */
    if (this.addForm.invalid) {
      return;
    }
    this.password = this.addForm.controls.password.value;
    let bal = this.addForm.controls.Amount.value;
    let userName = this.addForm.controls.user_name.value;
    /*
    This condition matches the password that user has intered and the passsword 
    that are present in the data base. If both are matched then only it allows to 
    go inside that block.
     */
    if (this.password == this.wallet.password) {
      /* now if the password is correct then it will take a conformation that you want to 
      transfer the amount into that perticular user name or not. */
      if (confirm(`Do You want to  Procced Transaction of ${bal} into ${this.addForm.controls.user_name.value} account`)) {
        this.processing = true;
        /* fundTransfer method passes all the details to backend data base and wait till the backend data
        base is processing that information and after getting the responce it shows the alert message. */
        this.services.fundTransfer(bal, userName).subscribe((data) => {
          alert(data);
          this.router.navigate(['/home']);
        },
          (err) => {
            /* if any kind of error occurs then it call the error the method and 
            accoordig to that it will show the popups */
            if (err.status == 400) {
              this.checkBalance = true;
            }
            if (err.status == 500) {
              this.UserNameValidation = true;
            }
            this.processing = false;
          })
      }
    }
    else {
      this.WrongpassWord = true;
    }
    this.checkBalance = false;

  }
  /* getValue is method that take the input from the list of user and set the 
  values in the forms data for further uses. */
  getValue(user) {
    this.addForm.controls.user_name.setValue(user.user_name);
    this.addForm.controls.mobile_no.setValue(user.mobile_no);
    this.addForm.controls.Amount.setValue(0);

  }

  /* logOutUser is a method that are used to logout and remove the sessionstorage data form the browser. */
  logOutUser() {
    this.auth.logOut();
    this.router.navigate(['/login']);
  }

}

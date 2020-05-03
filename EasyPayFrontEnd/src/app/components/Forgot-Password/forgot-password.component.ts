import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { Router } from '@angular/router';
import { CustomValidators } from 'src/app/model/custom-validators';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})

export class ForgotPasswordComponent implements OnInit {
  /* instance variabke to store the backend data. */
  walletAccount: WalletAccount;
  mobileNumber: string;
  emailId: string;
  UserNameValid: boolean = false;
  checkForm: FormGroup;
  passwordcheck: boolean = false;
  MobileValid: boolean = false;
  checkSubmitted: boolean = false;
  checkFormValid: boolean = false;
  notMatching: boolean = false;
  isUserName: boolean = false;
  validMobileEmail: boolean = false;
  count: number = 1;
  count1: number = 1;
  checkUserName: any;
  userName: string;
  checking: boolean = true;
  pEmail: string;
  pMobile: string;
  // Injecting all relevant services inside the constructor
  constructor(private formbuilder: FormBuilder, private service: UserService, private router: Router) { }

  ngOnInit() {
    /* building forms to take input by the user and change our data base details. validation are 
    required. */
    this.checkForm = this.formbuilder.group({
      UserName: ['', Validators.required],
      MobileNumber: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/[6-9]{1}/, { startWith: true }),
        Validators.pattern("[6-9][0-9]{9}"),
        CustomValidators.patternValidatorForNumber(/[A-Za-z]/, { hasNumber: true }),
        CustomValidators.patternValidatorForNumber(/[?=+.*/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        CustomValidators.patternValidatorForNumber(/ /, { containSpace: true }),
      ])],
      Email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/, { hasNumber: true }),
        CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
        CustomValidators.patternValidator(/[?=+.*/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        Validators.minLength(8)])
      ]
    });
  }

  /* check method is use to match the details that are entered by user with 
  the details that are present in the data base. */
  Check() {
    this.checkSubmitted = true;
    /* checking forms validation if form validation is correct
    then only this condition is allows you to go ahead and exexute the bellow line of code. */
    if (this.checkForm.controls.UserName.invalid) {
      return;
    }
    this.checking = false;
    /* getUserByUserName method take one argument and validate the user details and if 
    the details is correct then only it allows you to go ahead and change the password. */
    this.service.getUserByUserName(this.checkForm.controls.UserName.value).subscribe((data) => {
      /* if username is correct then this part of code will exicute and get the details of
      user which is associated with that username. */
      this.walletAccount = data;
      /* making the email id as code word form to display as a hint. */
      this.pEmail = this.walletAccount.emailId.charAt(0) + this.walletAccount.emailId.charAt(1) + this.walletAccount.emailId.charAt(2);
      for (let num = 3; num <= this.walletAccount.emailId.length - 3; num++) {
        if (this.walletAccount.emailId.charAt(num) == ".") {
          this.pEmail += this.walletAccount.emailId.charAt(num) + this.walletAccount.emailId.charAt(num + 1);
          num++;
          continue;
        }
        this.pEmail += '*';
      }
      /* making mobile number  as code word to display user as  hint. */
      this.pEmail += this.walletAccount.emailId.charAt(this.walletAccount.emailId.length - 2) + this.walletAccount.emailId.charAt(this.walletAccount.emailId.length - 1);
      this.pMobile = "******";
      for (let num = 6; num <= this.walletAccount.mobile_no.length; num++) {
        this.pMobile += this.walletAccount.mobile_no.charAt(num);
      }
      this.checking = true;
      this.userName = this.checkForm.controls.UserName.value;
      this.isUserName = false;
      this.checkFormValid = true;
      if (this.count == 1) {
        this.checkSubmitted = false;
        this.count++;
        return;
      }
      /* instance variable and function used to check the validity of mobile number and email id.
      if both are validated correctly then only this function allows to pass the control to ahead.  */
      if (this.checkForm.controls.MobileNumber.invalid || this.checkForm.controls.Email.invalid) {
        this.validMobileEmail = true;
        return;
      }
      if (this.checkForm.controls.MobileNumber.value == "" && this.checkForm.controls.Email.value == "") {
        return;
      }
      /* Now if Enterd details are matched with the data base then it allows user 
      to let user in and change the password. */
      if (this.checkForm.controls.MobileNumber.value == this.walletAccount.mobile_no && this.checkForm.controls.Email.value == this.walletAccount.emailId) {
        // instance variable used to handle UI part of the component.
        this.mobileNumber = this.checkForm.controls.MobileNumber.value;
        this.emailId = this.checkForm.controls.Email.value;
        this.notMatching = false;
        this.MobileValid = true;
        if (this.count1 == 1) {
          this.checkSubmitted = false;
          this.count1++;
          return;
        }
        /* now it will check the validation on the password. if validation success 
        then only it will allow to pass control. */
        if (this.checkForm.controls.password.invalid) {
          this.passwordcheck = true;
          return;
        }
        this.checking = false;
        /* UpdateUser is a method helps to update the password of user. */
        this.walletAccount.password = this.checkForm.controls.password.value;
        this.service.UpdateUser(this.walletAccount).subscribe((data) => {
          /* if details are correct then only it allows to make update of your password. */
          alert(data);
          this.router.navigate(['/login']);
        },
          (err) => {
            /* otherwise shows error in the alert. */
            alert(err.error);
          })
      }
      else {
        this.notMatching = true;
      }

    },
      (err) => {
        /* code exicute when any error in username is there. */
        // alert shows the error.
        if (err.error == null) {
          alert("UserName Must be Not null..!")
        }
        else {
          alert(err.error);
        }
        this.checking = true;
        if (this.checkForm.controls.UserName.value != "") {
          this.isUserName = true;
        }
        this.checkFormValid = false;
      })

    if (this.checkForm.invalid) {
      return;
    }
  }

}

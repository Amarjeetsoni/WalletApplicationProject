import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ErrorMessage } from 'src/app/model/ErrorMessage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  /* instance variable to make changes in the UI. */
  loginForm: FormGroup;
  submitted: boolean = false;
  check: any = false;
  num: number = 0;
  value: any;
  start: any;
  checkLogin: boolean = false;
  ErrorMsg: ErrorMessage;

  // Injecting all relevant services inside the constructor
  constructor(private formBuilder: FormBuilder, private router: Router, private service: UserService, private auth: AuthenticationService) {
  }
  ngOnInit() {
    /* making a reactive form with some validation in the form control. */
    this.loginForm = this.formBuilder.group({
      UserName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  /* method to redirect controler to add user page. */
  AddUser() {
    this.router.navigate(['/adduser']);
  }

  /* varify login function used to validate the the form and according to that pass the value
  to backend spring REST form which it get true value or some exception. */
  verifyLogin() {
    this.submitted = true;
    this.checkLogin = true;

    /* form validation checking */
    if (this.loginForm.invalid) {
      this.checkLogin = false;
      return;
    }
    let username = this.loginForm.controls.UserName.value;
    let password = this.loginForm.controls.password.value;
    /* checkLogin function takes the username and password as an argument
    and pass the details to backend srping REST and get some respaonce accordingly. */
    this.service.checkLogin(username, password).subscribe((data) => {
      /* if username and password is valid then this part of code is exicuted. */
      this.check = data;
      this.auth.authenticate(username, password);
      this.router.navigate(['home']);

    },
      (err) => {
        /* otherwise this exicuted. */
        this.checkLogin = false;
        this.invalidLogin = true;
        /* checkLogin and invalidLogin are the instance vaariable used to 
           handle UI part of the component. */

        /* err contains error message that are passed by spring REST from backend. */
        alert(err.error);

      });
  }
  invalidLogin: boolean = false;

}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ErrorMessage } from 'src/app/model/ErrorMessage';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {

  wallet: WalletAccount;
  errorHandle: ErrorMessage;
  user_name: string;
  constructor(private formbuilder: FormBuilder, private router: Router, private service: UserService, private auth: AuthenticationService) {
    // if username is null in the service then it automatically redirected to login page.
    if (this.service.user_name == null) {
      this.logOutUser();
    }
    this.user_name = this.service.user_name;
  }
  check: boolean;
  addForm: FormGroup;
  submitted: boolean = false;
  password: any;
  WrongPassword: boolean = false;
  processing: boolean = false;
  ngOnInit() {
    /* addForm is reactive form of angular which enable users to give the input on some specific conditions. */
    this.addForm = this.formbuilder.group({
      Amount: ['', [Validators.required, Validators.min(1)]],
      password: ['', Validators.required]
    });
  }
  /**
   * WithdrawAmount method call when user enter the amount and password and press on withdraw button. 
   * */
  WithdrawAmount() {
    this.submitted = true;
    this.WrongPassword = false;
    let money = this.addForm.controls.Amount.value;
    /* validate the forms condition if true then only it allows to move further lines of code.*/
    if (this.addForm.invalid) {
      return;
    }
    this.password = this.addForm.controls.password.value;
    if (this.password == this.service.password) {
      this.processing = true;
      if (confirm(`Do You Want to Withdraw ${money} from your Account...`)) {

        /* by using subscribe we can track any changes that are happing in the database.
        and data part is exicuted when spring rest return any data other wise if any exception occured 
        then err part is exicuted.
        */
        this.service.withdrawMoney(money).subscribe((data) => {
          alert(data);
          this.router.navigate(['/home']);
        },
          (err) => {
            alert("Error message: " + err.error);
            /*err object being return form spring REST is being
            pop up as an arert in UI part of the component.
            */
            this.check = true;
            this.processing = false;
            /* check and processing varible is used to handle UI part of the component.*/
          }
        );
      }
    }
    else {
      this.WrongPassword = true;
    }

  }
  /**
   * logOutUser method used to call logout method of authGuard services.
   * which remove the seeionstorage variable.
   */
  logOutUser() {
    this.auth.logOut();
    this.router.navigate(['/login']);
  }

}

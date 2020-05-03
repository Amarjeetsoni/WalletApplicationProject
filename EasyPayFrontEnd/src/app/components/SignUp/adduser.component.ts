import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletAccount } from 'src/app/model/WalletAccount';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CustomValidators } from 'src/app/model/custom-validators';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.css']
})
export class AdduserComponent implements OnInit {

  addForm: FormGroup;
  submitted: boolean = false;
  wallet: WalletAccount;
  checkEmail: any = false;
  checkMobile: any = false;
  checkUserName: any = false;
  checkEmail1: any = false;
  checkMobile1: any = false;
  checkUserName1: any = false;
  gendervalid: boolean = false;
  checkValue: number = 1;
  num: number = 1;
  date = new Date();
  // Injecting all relevant services inside the constructor
  constructor(private formBuilder: FormBuilder,
    private router: Router, private service: UserService, private auth: AuthenticationService) {
    this.auth.logOut();
  }

  // Life Cycle Hook
  ngOnInit() {
    /* addForm is reactive form of angular which enable users to give the input on some specific conditions. */
    this.addForm = this.formBuilder.group({
      firstName: ['', Validators.compose([
        Validators.required,
        Validators.minLength(3),
        CustomValidators.patternValidator(/[A-Z]{1}/, { startWith: true }),
        CustomValidators.patternValidatorForNumber(/\d/, { hasNumber: true }),
        CustomValidators.patternValidatorForNumber(/[?=+.`*-/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        CustomValidators.patternValidatorForNumber(/ /, { containSpace: true }),
      ])],
      middleName: ['', Validators.compose([CustomValidators.patternValidatorForNumber(/\d/, { hasNumber: true }),
      CustomValidators.patternValidator(/[A-Za-z]/, { startWith: true }),
      CustomValidators.patternValidatorForNumber(/[?=+-`*/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
      CustomValidators.patternValidatorForNumber(/ /, { containSpace: true }),
      ])],
      lastName: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidatorForNumber(/\d/, { hasNumber: true }),
        CustomValidators.patternValidatorForNumber(/[?=+.*-/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        CustomValidators.patternValidatorForNumber(/ /, { containSpace: true })
      ])],
      dob: ['', Validators.compose([
        Validators.required,
        Validators.min(10),
        Validators.max(100),
      ])],
      gender: ['', Validators.required],
      mobile_no: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/[6-9]{1}/, { startWith: true }),
        Validators.pattern("[6-9][0-9]{9}"),
        CustomValidators.patternValidatorForNumber(/[A-Za-z]/, { hasNumber: true }),
        CustomValidators.patternValidatorForNumber(/[?=+.*-/'":-;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        CustomValidators.patternValidatorForNumber(/ /, { containSpace: true }),
      ])],
      emailId: ['', [Validators.required, Validators.email]],
      user_name: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/[A-Za-z]/, { startWith: true }),
        CustomValidators.patternValidatorForNumber(/ /, { containSpace: true }),
        Validators.minLength(4)
      ])],
      password: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/, { hasNumber: true }),
        CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
        CustomValidators.patternValidator(/[?=+.*-/'":;<>~_|[\]{}\\!@#$%^&*()]/, { hasSpecialCharacters: true }),
        Validators.minLength(8)])
      ]
    });
  }


  /*
    addUser method call once you hit the adduser button on UI part. 
    It will validate all the condition of the form pass all the details to 
    spring rest backend database and according to return responce from backend 
    it will show the response on UI.
   */
  addUser() {
    this.submitted = true;
    this.checkValue = 3;
    /*
    If form is valid then only this function allows to access bellow line of code otherwise 
    it simply return the control back.
    */
    if (this.addForm.invalid) {
      this.checkValue = 1;
      return;
    }
    /* These are instance variable that is being use to design and change the content of UI.*/
    let email1 = this.addForm.controls.emailId.value;
    let mobile_no1 = this.addForm.controls.mobile_no.value;
    let user_name1 = this.addForm.controls.user_name.value;
    let numbervisit = 0;

    /* checkEmail used to check Email from backend data base. 
       and subscribe method is used to look the responce from backend. */
    this.service.checkEmail(email1).subscribe((data) => {
      /* if email is not present then this part of code will exicute */
      this.checkEmail = false;
      this.checkEmail1 = true;
      if (this.checkValue == 3 && numbervisit == 2) {
        this.checkValue = 2;
      }
      numbervisit++;
    },
      (err) => {
        /* otherwise this part of code will exicute */
        this.checkEmail = true;
        this.checkValue = 1;
        /* checkEmail and checkValue varible is used to handle UI part of the component.*/
        numbervisit++;
      })

    /* checkMobile used to check Mobile number from backend data base. 
      and subscribe method is used to look the responce from backend. */
    this.service.checkMobile(mobile_no1).subscribe((data) => {
      /* if Mobile Number is not present then this part of code will exicute */
      this.checkMobile = false;
      this.checkMobile1 = true;
      if (this.checkValue == 3 && numbervisit == 2) {
        this.checkValue = 2;
      }
      numbervisit++;
    },
      (err) => {
        /* otherwise this part of code will exicute */
        this.checkMobile = true;
        this.checkValue = 1;
        /* checkEmail and checkValue varible is used to handle UI part of the component.*/
        numbervisit++;
      })

    /* checkAccount used to check userNmaefrom backend data base. 
       and subscribe method is used to look the responce from backend. */
    this.service.checkAccount(user_name1).subscribe((data) => {
      /* if username is not present then this part of code will exicute */
      this.checkUserName = false;
      this.checkUserName1 = true;
      if (this.checkValue == 3 && numbervisit == 2) {
        this.checkValue = 2;
      }
      numbervisit++;
    },
      (err) => {
        /* otherwise this part of code will exicute */
        this.checkUserName = true;
        this.checkValue = 1;
        numbervisit++;
      })
    if (this.checkEmail1 == false || this.checkMobile1 == false || this.checkUserName1 == false) {
      return;
    }
    else {
      this.checkValue = 2;
    }
    /* instance varible is used to handle UI part of the component.*/
    this.checkEmail = false;
    this.checkMobile = false;
    this.checkUserName = false;
    this.checkValue = 4;
    /* addUser method is used to pass user details to the backend data base and
       subscribe method is used to look the responce from backend. */
    this.service.addUser(this.addForm.value).subscribe((data) => {
      /*If backend return some data then this part exicute */
      alert(data);
      this.router.navigate(['/home']);
    },
      (err) => {
        /* otherwise this part of code will exicute. */
        alert(err.error);
      })


  }

}

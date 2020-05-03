import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home2',
  templateUrl: './home2.component.html',
  styleUrls: ['./home2.component.css']
})
export class Home2Component implements OnInit {

  // Injecting all relevant services inside the constructor
  constructor(private router: Router, private auth: AuthenticationService) {
    this.auth.logOut();
  }

  ngOnInit() {
  }
  // login method is called to navigate login page.
  loginpage() {
    this.router.navigate(['/login']);
  }

}

import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGaurdService implements CanActivate {

  constructor(private router: Router, private auth: AuthenticationService) { }
  /*
     canActivate function is use to decide weather any path user is logged in or not.
     if user is logged in then only it will give the access of that path.
     otherwise it redirect user to login page.
     any user can not access their data after logout.
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.auth.isUserLoggedIn()) {
      console.log("this is AuthGaurdService" + this.auth.isUserLoggedIn());
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }
}

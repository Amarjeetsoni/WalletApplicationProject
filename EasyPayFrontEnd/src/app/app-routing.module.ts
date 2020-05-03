import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/HomePage1/home.component';
import { LoginComponent } from './components/login/login.component';
import { Home2Component } from './components/home2/home2.component';
import { AdduserComponent } from './components/SignUp/adduser.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DepositAmountComponent } from './components/deposit-amount/deposit-amount.component';
import { FundTransferComponent } from './components/fund-transfer/fund-transfer.component';
import { WithdrawComponent } from './components/withdraw/withdraw.component';
import { PrintComponent } from './components/Print-Transaction/print.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { AuthGaurdService } from './services/auth-gaurd.service';
import { HomePageComponent } from './components/home-page/home-page.component';

// defines routers for your components
//defines array of objects of routers
const routes: Routes = [
  { path: 'adduser', component: AdduserComponent },
  { path: 'print', component: PrintComponent, canActivate: [AuthGaurdService] },     // in this we are using authgaurd service  to ristrict user not to access other routing path without login.
  { path: 'deposit', component: DepositAmountComponent, canActivate: [AuthGaurdService] },
  { path: 'withdraw', component: WithdrawComponent, canActivate: [AuthGaurdService] },
  { path: 'fund', component: FundTransferComponent, canActivate: [AuthGaurdService] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGaurdService] },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGaurdService] },
  { path: 'forgot', component: ForgotPasswordComponent },
  { path: 'HomePage', component: HomePageComponent },
  { path: '**', component: Home2Component }             // generic path for all the request that are not pathed by above path.
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

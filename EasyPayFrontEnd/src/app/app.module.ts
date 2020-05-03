import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ReactiveFormsModule, FormsModule, NgSelectOption } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSidenavModule } from '@angular/material';
import { FilterUserNamePipe } from './Pipe/filter-user-name.pipe';
import { HomePageComponent } from './components/home-page/home-page.component'


const material = [
  MatSidenavModule
];
@NgModule({
  declarations: [                      // registering all userdefined components, directives, pipes
    AppComponent,
    HomeComponent,
    LoginComponent,
    Home2Component,
    AdduserComponent,
    ProfileComponent,
    DepositAmountComponent,
    FundTransferComponent,
    WithdrawComponent,
    PrintComponent,
    ForgotPasswordComponent,
    FilterUserNamePipe,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSidenavModule
  ],
  providers: [],                        // Registering userdefined services
  bootstrap: [AppComponent]             // Specifies stratup component
})
export class AppModule { }

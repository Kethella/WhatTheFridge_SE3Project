import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';

import { AuthLayoutComponent } from './auth-layout.component';

import { SignUpComponent } from 'src/app/modules/account-auth/sign-up/sign-up.component';
import { LoginComponent } from 'src/app/modules/account-auth/login/login.component';

import { PersonalMaterialModule } from 'src/app/material.module';


@NgModule({
  declarations: [
    AuthLayoutComponent,
    SignUpComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FlexLayoutModule,
    PersonalMaterialModule,
    FormsModule
  ]
})
export class AuthLayoutModule { }

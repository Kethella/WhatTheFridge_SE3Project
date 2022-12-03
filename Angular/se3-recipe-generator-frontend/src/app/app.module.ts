import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AccountComponent } from './components/account/account.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { DummyPagesComponent } from './components/dummy-pages/dummy-pages.component';
import { SignUpComponent } from './components/account/sign-up/sign-up.component';
import { LoginComponent } from './components/account/login/login.component';

import { PersonalMaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    NavbarComponent,
    DummyPagesComponent,
    SignUpComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    PersonalMaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

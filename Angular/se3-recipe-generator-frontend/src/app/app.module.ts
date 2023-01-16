import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonalMaterialModule } from './material.module';
import { LoggedInLayoutModule } from './layouts/logged-in-layout/logged-in-layout.module';
import { AuthLayoutModule } from './layouts/auth-layout/auth-layout.module';
import { SignUpComponent } from './modules/account-auth/sign-up/sign-up.component';
import { LoginComponent } from './modules/account-auth/login/login.component';
import { DummyPagesComponent } from './modules/dummy-pages/dummy-pages.component';
import { AccountViewComponent } from './modules/account-view/account-view.component';
import { UpdateProfileComponent } from './modules/update-profile/update-profile.component';


@NgModule({
  declarations: [
    AppComponent,
    UpdateProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    PersonalMaterialModule,
    LoggedInLayoutModule,
    AuthLayoutModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

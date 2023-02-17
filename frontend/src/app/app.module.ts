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



@NgModule({
  declarations: [
    AppComponent,
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

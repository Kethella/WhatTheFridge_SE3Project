import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';

import { AuthLayoutComponent } from './auth-layout.component';

import { SignUpComponent } from 'src/app/modules/account-auth/sign-up/sign-up.component';
import { LoginComponent } from 'src/app/modules/account-auth/login/login.component';

import { PersonalMaterialModule } from 'src/app/material.module';
import { RecipeGridComponent } from 'src/app/modules/home/recipe-grid/recipe-grid.component';
import { RecipeSortFilterComponent } from 'src/app/modules/home/recipe-sort-filter/recipe-sort-filter.component';


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
    PersonalMaterialModule
  ]
})
export class AuthLayoutModule { }

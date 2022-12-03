import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';

import { LoggedInLayoutComponent } from './logged-in-layout.component';
import { HomeComponent } from 'src/app/modules/home/home.component';
import { DummyPagesComponent } from 'src/app/modules/dummy-pages/dummy-pages.component';

import { SharedModule } from 'src/app/shared/shared.module';
import { PersonalMaterialModule } from 'src/app/material.module';


@NgModule({
  declarations: [
    LoggedInLayoutComponent,
    HomeComponent,
    DummyPagesComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FlexLayoutModule,
    SharedModule,
    PersonalMaterialModule
  ]
})
export class LoggedInLayoutModule { }

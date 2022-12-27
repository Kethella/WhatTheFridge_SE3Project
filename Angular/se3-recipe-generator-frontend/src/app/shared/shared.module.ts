import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';

import { HeaderComponent } from './components/header/header.component';
import { PersonalMaterialModule } from '../material.module';
import { RecipeDetailsComponent } from './components/recipe-details/recipe-details.component';



@NgModule({
  declarations: [
    HeaderComponent,
    RecipeDetailsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FlexLayoutModule,
    PersonalMaterialModule
  ],
  exports: [
    HeaderComponent,
    RecipeDetailsComponent
  ]
})
export class SharedModule { }

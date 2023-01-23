import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { LoggedInLayoutComponent } from './logged-in-layout.component';
import { HomeComponent } from 'src/app/modules/home/home.component';
import { DummyPagesComponent } from 'src/app/modules/dummy-pages/dummy-pages.component';
import { EditFridgeItemDialog, FridgeComponent, NewFridgeItemDialog } from 'src/app/modules/fridge/fridge.component';

import { SharedModule } from 'src/app/shared/shared.module';
import { PersonalMaterialModule } from 'src/app/material.module';
import { RecipeGridComponent } from 'src/app/modules/home/recipe-grid/recipe-grid.component';
import { RecipeFilterDialog, RecipeSortFilterComponent } from 'src/app/modules/home/recipe-sort-filter/recipe-sort-filter.component';
import { MyRecipesComponent } from 'src/app/modules/my-recipes/my-recipes.component';
import { RecipeListComponent } from 'src/app/modules/my-recipes/recipe-list/recipe-list.component';
import { CreateRecipeComponent } from 'src/app/modules/my-recipes/create-recipe/create-recipe.component';
import { EditRecipeComponent } from 'src/app/modules/my-recipes/recipe-list/edit-recipe/edit-recipe.component';

import { AccountViewComponent } from 'src/app/modules/account-view/account-view.component';


@NgModule({
  declarations: [
    LoggedInLayoutComponent,
    HomeComponent,
    DummyPagesComponent,
    RecipeGridComponent,
    RecipeSortFilterComponent,
    RecipeFilterDialog,
    MyRecipesComponent,
    RecipeListComponent,
    FridgeComponent,
    NewFridgeItemDialog,
    EditFridgeItemDialog,
    CreateRecipeComponent,
    EditRecipeComponent,
    AccountViewComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FlexLayoutModule,
    FormsModule,
    SharedModule,
    PersonalMaterialModule,
  ]
})
export class LoggedInLayoutModule { }

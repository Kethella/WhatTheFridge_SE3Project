import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {
  selectedRecipe: Recipe;
  ingredients: Ingredient[];
  ingredientsAsStrings: string[];

  constructor(
    public dialogRef: MatDialogRef<RecipeDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
      this.ingredients = [];
      this.ingredientsAsStrings = [];
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public icon = 'favorite_border'
  markFavourite(newIcon: string){
    this.icon = newIcon;
  }

  ngOnInit() {
    this.selectedRecipe = this.data.selectedRecipe;
    this.ingredients = this.generateIngredients(this.selectedRecipe);
    this.ingredientsAsStrings = this.generateIngredientsAsStrings(this.ingredients);
  }

  onCloseClick() {
    this.dialogRef.close();
  }

  generateIngredients(recipe: Recipe): Ingredient[]{

    recipe.ingredientNames.forEach((value, index) => {
      var temp: Ingredient = {
        "name": value,
        "measure": recipe.ingredientMeasures[index]
      }
      this.ingredients.push(temp);
    });

    return this.ingredients;
  }

  generateIngredientsAsStrings(ingredients: Ingredient[]): string[] {
    ingredients.forEach((value) => {
      var temp: string = value.name;
      if(value.measure != "" && value.measure != null){
        temp = temp.concat(" - " + value.measure)
      }
      this.ingredientsAsStrings.push(temp);
    });

    return this.ingredientsAsStrings;
  }
}

export interface DialogData {
  selectedRecipe: Recipe
}

export interface Ingredient {
  name: string;
  measure: string;
}

import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { HttpParams } from '@angular/common/http';
import { Component, ViewChild, Inject, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ICategory } from 'src/app/models/category';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-edit-recipe',
  templateUrl: './edit-recipe.component.html',
  styleUrls: ['./edit-recipe.component.css']
})
export class EditRecipeComponent {

  @Output() public restartAfterEdit = new EventEmitter();

  recipe: Recipe;

  ingredientName: string;
  ingredientNames: string[];
  ingredientAmount: string;
  ingredientAmounts: string[];
  ingredients: Ingredient[];

  tags: string;

  selectedCategory: ICategory = {"enumValue": "", "text":""};
  categories: ICategory[];

  selectedTags: string[];

  displayedColumns: string[] = ['ingredientName', 'ingredientAmount', 'actions'];
  tagsAsString: string;
  splitTagsArr:string[];

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  @ViewChild(MatTable) table: MatTable<Ingredient>;
  ingredient: Ingredient = {
    "ingredientName": '',
    "ingredientAmount": ''
  }

  constructor(public dialogRef:MatDialogRef<EditRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService){
      this.ingredientNames = []
      this.ingredientAmounts = []
      this.ingredients = [],
      this.selectedTags =[]
 }

  async ngOnInit(){
    this.recipe= this.data.selectedRecipe;

    this.categories = await this._recipeService.getCategories();
    this.selectedCategory = this.getSelectedCategory(this.recipe.category)

    this.ingredientNames = this.recipe.ingredientNames
    this.ingredientAmounts = this.recipe.ingredientMeasures

    this.ingredients = this.getIgredients(this.ingredientNames, this.ingredientAmounts)
  }

  onCloseClick() {
    this.dialogRef.close();
  }

  async onSubmit(){

    this.selectedCategory = this.setSelectedCategory(this.selectedCategory)
    console.log(this.selectedCategory)
    this.recipe.category = this.selectedCategory.enumValue

    this.recipe = await this._recipeService.updateRecipe(this.recipe);
    console.log(this.recipe)
    this.dialogRef.close();
 }



  /*  --------------------------------------------------------------------------------
        INGREDIENT METHODS
      --------------------------------------------------------------------------------
  */

  getIgredients(ingredientNames: string[], ingredientAmounts: string[]): Ingredient[] {

    let returnIngr: Ingredient[] = [];

    ingredientNames.forEach(function (value, index) {
      let temp: Ingredient = {
        ingredientName: value,
        ingredientAmount: ingredientAmounts.at(index)!
      }
      returnIngr.push(temp)
    });

    return returnIngr;
  }

  async onAddIngredient(){
    var name = this.ingredientName
    var amount = this.ingredientAmount

    this.ingredientNames.push(name)
    this.ingredientAmounts.push(amount)

    let ingredient: Ingredient = {
      ingredientName: name,
      ingredientAmount: amount
    }

    this.ingredients.push(ingredient)
    this.table.renderRows();

  }

  onDeleteIngredient(element: Ingredient){

    let deleteIndex = -1

    this.ingredients.forEach(function (value, index) {
      if(value.ingredientName === element.ingredientName && value.ingredientAmount === element.ingredientAmount) {
        deleteIndex = index
      }
    });

    this.ingredientNames.splice(deleteIndex, 1)
    this.ingredientAmounts.splice(deleteIndex, 1)
    this.ingredients.splice(deleteIndex, 1)
    this.table.renderRows();
    console.log(this.ingredients)


  }



  /*  --------------------------------------------------------------------------------
        CATEGORY METHODS
      --------------------------------------------------------------------------------
  */

  setSelectedCategory(selectedCategory: ICategory): ICategory{

    for (let category of this.categories){
      if (selectedCategory.text == category.text){
        selectedCategory.enumValue = category.enumValue;
      }
    }
    return selectedCategory;
  }

  getSelectedCategory(categoryEnum: string): ICategory{

    for (let category of this.categories){
      if (categoryEnum == category.enumValue){
        const returnCategory: ICategory = {
          text: category.text,
          enumValue: category.enumValue
        }
        return returnCategory;
      }
    }
    throw Error;
  }



  /*  --------------------------------------------------------------------------------
        TAG METHODS
      --------------------------------------------------------------------------------
  */
  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.recipe.tags.push(value);
    }

    event.chipInput!.clear();
    console.log(this.recipe.tags)
  }

  removeTag(tag: string): void {
    const index = this.recipe.tags.indexOf(tag);

    if (index >= 0) {
      this.recipe.tags.splice(index, 1);
    }
    console.log(this.recipe.tags)
  }
}

export interface Ingredient {
  ingredientName: string;
  ingredientAmount: string;
}

export interface tableColumns
{
    ingredientName: string,
    ingredientAmount: string;
    actions: string
}



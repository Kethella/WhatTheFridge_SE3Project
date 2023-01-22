import { Component, EventEmitter, Inject, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { DialogData, RecipeDetailsComponent } from 'src/app/shared/components/recipe-details/recipe-details.component';
import {MatTable} from '@angular/material/table';
import { ICategory } from 'src/app/models/category';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent {
  recipeForm: FormGroup;

  recipe: Recipe;

  ingredienNames: string[];
  ingredientAmounts: string[];
  ingredients: Ingredient[];

  selectedCategory: ICategory = {"enumValue": "", "text":""};
  categories: ICategory[];

  selectedTags: string[];

  displayedColumns: string[] = ['ingredientName', 'ingredientAmount'];

  public queryParams = new HttpParams();
  @Output() public newQueryEvent = new EventEmitter<HttpParams>();

  @ViewChild(MatTable) table: MatTable<Ingredient>;

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService){

      this.ingredienNames = []
      this.ingredientAmounts = []
      this.ingredients = [],
      this.selectedTags =[]
 }

  async ngOnInit(){
    this.recipeForm=this._formBuilder.group({
      ingredientName:[null, Validators.required],
      ingredientAmount:[null, Validators.required],
      name:[null, Validators.required],
      category:[null, Validators.required],
      tags:[null, Validators.required],
      instructions:[null, Validators.required]
    })

    this.categories = await this._recipeService.getCategories();
  }

  onCloseClick() {
    this.dialogRef.close();
  }
  onCancel(){
    this.dialogRef.close();
  }

  async onSubmit(){

    this.selectedCategory = this.setSelectedCategory(this.selectedCategory)

    this.recipe={
      id: '',
      name:this.recipeForm.get('name')?.value,
      ingredientNames: this.ingredienNames,
      ingredientMeasures: this.ingredientAmounts,
      category: this.selectedCategory.enumValue,
      tags: this.selectedTags,
      instructions:this.recipeForm.get('instructions')?.value,
      image:'http://localhost:8085/media/download/63c95e3d664c9260ee663f9c',
      link:'',
      ownerAccount:''
    }
    this.recipe = await this._recipeService.createRecipe(this.recipe);
    console.log(this.recipe)
    this.dialogRef.close();
 }


  async onAddIngredient(){
    var name = this.recipeForm.get("ingredientName")?.value
    var amount = this.recipeForm.get("ingredientAmount")?.value

    this.ingredienNames.push(name)
    this.ingredientAmounts.push(amount)

    const ingredient: Ingredient = {
      ingredientName: name,
      ingredientAmount: amount
    }

    this.ingredients.push(ingredient)
    this.table.renderRows();
  }

  setSelectedCategory(selectedCategory: ICategory): ICategory{

    for (let category of this.categories){
      if (selectedCategory.text == category.text){
        selectedCategory.enumValue = category.enumValue;
      }
    }
    return selectedCategory;
  }

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.selectedTags.push(value);
    }

    event.chipInput!.clear();
  }

  removeTag(tag: string): void {
    const index = this.selectedTags.indexOf(tag);

    if (index >= 0) {
      this.selectedTags.splice(index, 1);
    }
  }
}

export interface Ingredient {
  ingredientName: string;
  ingredientAmount: number;
}

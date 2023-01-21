import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ViewChild, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ICategory } from 'src/app/models/category';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { CreateRecipeComponent } from '../create-recipe/create-recipe.component';

@Component({
  selector: 'app-edit-recipe',
  templateUrl: './edit-recipe.component.html',
  styleUrls: ['./edit-recipe.component.css']
})
export class EditRecipeComponent {
  recipeForm: FormGroup;

  recipe: Recipe;
  ingredientNames: String[];
  ingredientAmounts: String[];
  ingredients: Ingredient[];
  selectedCategory: ICategory = {"enumValue": "", "text":""};
  categories: ICategory[];

  displayedColumns: string[] = ['ingredientName', 'ingredientAmount'];

  @ViewChild(MatTable) table: MatTable<Ingredient>;

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService){

      this.ingredientNames = []
      this.ingredientAmounts = []
      this.ingredients = []
 }

  async ngOnInit(){
  this.recipeForm=this._formBuilder.group({
    ingredientName:[null, Validators.required],
    ingredientAmount:[null, Validators.required],
    name:[null, Validators.required],
    category:[null, Validators.required],
    tags:[null, Validators.required],
    instructions:[null, Validators.required],

  })
  this.categories = await this._recipeService.getCategories();
 }

  onCloseClick() {
    this.dialogRef.close();
  }

  async onSubmit(){

    this.recipe={
      id: '',
      name:'neshto novo',
      category:'MAINCOURSE',
      instructions:'idk',
      image:'http://localhost:8085/media/download/63c95e3d664c9260ee663f9c',
      tags: this.recipeForm.get('tags')?.value,
      link:'',
      ingredientMeasures: ["manja", "o6te ne6to"],
      ingredientNames: ["1", "1"],
      ownerAccount:''
    }

    this.recipe = await this._recipeService.updateRecipe(this.recipe);
    console.log(this.recipe)
    this.dialogRef.close()
 }

  async onAdd(){

      var name = this.recipeForm.get("ingredientName")?.value
      var amount = this.recipeForm.get("ingredientAmount")?.value
      console.log(name)
      this.ingredientNames.push(name)
      this.ingredientAmounts.push(amount)
      const ingredient: Ingredient = {
        ingredientName: name,
        ingredientAmount: amount
      }
      this.ingredients.push(ingredient)
      this.table.renderRows();

  }

  setSelectedCategory(categories: ICategory[], selectedCategory: ICategory): ICategory{

    for (let category of categories){
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
  tagsArray: Tags[]=[];

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.tagsArray.push({name: value.trim()});
    }

    if (input) {
      input.value = '';
    }
  }

  remove(tag: Tags): void {
    const index = this.tagsArray.indexOf(tag);

    if (index >= 0) {
      this.tagsArray.splice(index, 1);
    }
  }

}


export interface Ingredient {
  ingredientName: string;
  ingredientAmount: number;
}

export interface Tags{
  name: string;
}



import { Component, Inject, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { DialogData, RecipeDetailsComponent } from 'src/app/shared/components/recipe-details/recipe-details.component';
import {MatTable} from '@angular/material/table';


@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent {
  recipeForm: FormGroup;

  recipe: Recipe;
  ingredienNames: String[];
  ingredientAmounts: String[];
  ingredients: Ingredient[];

  displayedColumns: string[] = ['ingredientName', 'ingredientAmount'];

  @ViewChild(MatTable) table: MatTable<Ingredient>;

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService){

      this.ingredienNames = []
      this.ingredientAmounts = []
      this.ingredients = []
 }

 ngOnInit(){
  this.recipeForm=this._formBuilder.group({
    ingredientName:[null, Validators.required],
    ingredientAmount:[null, Validators.required]
  })
 }

  onCloseClick() {
    this.dialogRef.close();
  }

  onSubmit(){
  if(this.recipeForm.valid){
    this.recipe={
      ingredientNames: this.recipeForm.get('ingredient')?.value,
      id: '',
      name:'manja',
      category:'random',
      instructions:'idk',
      image:'http://localhost:8085/media/download/63c95e3d664c9260ee663f9c',
      tags: [],
      link:'',
      ingredientMeasures:this.recipeForm.get('amount')?.value,
      ownerAccount:''
    }
    //this._recipeService.save(this.recipe);

    console.log(this.recipe)
  }
 }



  async onAdd(){

    if (this.recipeForm.valid) {
      var name = this.recipeForm.get("ingredientName")?.value
      var amount = this.recipeForm.get("ingredientAmount")?.value
      console.log(name)
      this.ingredienNames.push(name)
      this.ingredientAmounts.push(amount)
      const ingredient: Ingredient = {
        ingredientName: name,
        ingredientAmount: amount
      }
      this.ingredients.push(ingredient)
      this.table.renderRows();
    }
    else {
      //TODO: error message
    }
  }





}

export interface Ingredient {
  ingredientName: string;
  ingredientAmount: number;
}

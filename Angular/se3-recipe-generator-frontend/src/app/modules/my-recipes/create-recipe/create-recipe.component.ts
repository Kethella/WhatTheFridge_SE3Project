import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { DialogData, RecipeDetailsComponent } from 'src/app/shared/components/recipe-details/recipe-details.component';

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent {
  recipeForm: FormGroup;

  recipe: Recipe;

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
  @Inject(MAT_DIALOG_DATA) public data:any, private _formBuilder: FormBuilder, private _recipeService:RecipeService){
 }

 ngOnInit(){
  this.recipeForm=this._formBuilder.group({
    ingredient:[null, Validators.required],
    amount:[null, Validators.required]
  })
 }

 onCloseClick() {
  this.dialogRef.close();
  }

  onSubmit(){
  if(this.recipeForm.valid){
    this.recipe={
      ingredientNames: this.recipeForm.get('ingredient')?.value, 
      id: '1',
      name:'manja',
      category:'random',
      instructions:'idk',
      image:'snimka',
      tags: [],
      link:'',
      ingredientMeasures:this.recipeForm.get('amount')?.value,
      ownerAccount:'2238550034095900'
    }
    this._recipeService.save(this.recipe);
    
    console.log(this.recipe)
  }
 }
}

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
import { CreateRecipeComponent } from '../create-recipe/create-recipe.component';

@Component({
  selector: 'app-edit-recipe',
  templateUrl: './edit-recipe.component.html',
  styleUrls: ['./edit-recipe.component.css']
})
export class EditRecipeComponent {
  recipeForm: FormGroup;
  
  recipe: Recipe;
  name: string;
  ingredienNames: string[];
  ingredientAmounts: string[];
  ingredients: Ingredient[];

  selectedCategory: ICategory = {"enumValue": "", "text":""};
  categories: ICategory[];

  selectedTags: string[];
  
  displayedColumns: string[] = ['ingredientName', 'ingredientAmount', 'actions'];
  tagsAsString: string;
  splitTagsArr:string[];
  public queryParams = new HttpParams();
  selectedRecipe: Recipe;
  @Output() public newQueryEvent = new EventEmitter<HttpParams>();

  @ViewChild(MatTable) table: MatTable<Ingredient>;
  ingredient: Ingredient = {
    "ingredientName": '',
    "ingredientAmount": ''
  }

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService){
      this.name=''
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
    this.selectedRecipe= this.data.selectedRecipe;
    this.name = this.selectedRecipe.name
  }

  onCloseClick() {
    this.dialogRef.close();
  }

  splitTags(){
    this.tagsAsString = this.data.selectedTags.toString();
    this.splitTagsArr = this.tagsAsString.split(", ");
    console.log(this.splitTagsArr)
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
    this.recipe = await this._recipeService.updateRecipe(this.recipe);
    console.log(this.recipe)
    this.dialogRef.close();
 }


  async onAddIngredient(){
    var name = this.recipeForm.get("ingredientName")?.value
    var amount = this.recipeForm.get("ingredientAmount")?.value

    this.ingredienNames.push(name)
    this.ingredientAmounts.push(amount)

    let ingredient: Ingredient = {
      ingredientName: name,
      ingredientAmount: amount
    }

    this.ingredients.push(ingredient)
    this.table.renderRows();

  }

  onDeleteIngredient(index:any){
    console.log(this.ingredients)
      this.ingredients.splice(index,1)
      
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
  ingredientAmount: string;
}

export interface tableColumns 
{ 
    ingredientName: string,
    ingredientAmount: string;
    actions: string 
}



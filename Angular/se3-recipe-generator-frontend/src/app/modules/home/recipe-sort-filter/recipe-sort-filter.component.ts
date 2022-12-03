import { Component, Output, EventEmitter, Inject, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatChipEditedEvent, MatChipInputEvent} from '@angular/material/chips';
import {ENTER, COMMA} from '@angular/cdk/keycodes';
import { RecipeService } from 'src/app/services/recipe.service';




@Component({
  selector: 'app-recipe-sort-filter',
  templateUrl: './recipe-sort-filter.component.html',
  styleUrls: ['./recipe-sort-filter.component.css']
})
export class RecipeSortFilterComponent{

  @Output() public newQueryEvent = new EventEmitter<HttpParams>();


  public queryParams = new HttpParams();
  selectedCategory: string;
  selectedTags: string;
  selectedTagsAsArray: string[];
  selectedIngredients: string;
  tags: string[];

  constructor(public dialog: MatDialog) {
    this.selectedCategory = "";
    this.selectedIngredients = "";
    this.selectedTags = "";
    this.selectedTagsAsArray = [];
    this.tags = [];
  }



  query(){
    if (this.selectedCategory){

      this.queryParams = this.queryParams.append("category",this.selectedCategory);
      console.log(this.selectedCategory);

    }
    else {
      console.log('no category');
    }
    if (this.selectedTags){

      this.queryParams = this.queryParams.append("tags",this.selectedTags);
      console.log(this.selectedTags);

    }
    else {
      console.log('no tags');
    }
    if (this.selectedIngredients){

      this.queryParams = this.queryParams.append("ingredientNames",this.selectedIngredients);
      console.log(this.selectedIngredients);

    }
    else {
      console.log('no ingredients');
    }


    this.newQueryEvent.emit(this.queryParams);

  }


  openDialog(): void {
    const dialogRef = this.dialog.open(RecipeFilterDialog, {
      width: '900px',
      data: {
        selectedCategory: this.selectedCategory,
        selectedTagsAsArray: this.selectedTagsAsArray},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.selectedCategory = result.selectedCategory;
      this.selectedTagsAsArray = result.selectedTagsAsArray;

      if (!this.selectedTagsAsArray.length){
        console.log("maikati")
        this.selectedTags = "";
      }
      this.selectedTagsAsArray.forEach((value: string, index: number) => {
        if (index == 0){
          this.selectedTags = value;
        }
        else {
          this.selectedTags = this.selectedTags.concat("," + value)
        }
      });

      this.queryParams = this.queryParams.delete('category');
      this.queryParams = this.queryParams.delete('tags');

      this.query();
    });
  }








  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  ingredientsAsArray: Ingredient[] = [];

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.ingredientsAsArray.push({name: value});
      this.updateQuery();
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(ingredient: Ingredient): void {
    const index = this.ingredientsAsArray.indexOf(ingredient);

    if (index >= 0) {
      this.ingredientsAsArray.splice(index, 1);
      this.updateQuery();
    }
  }

  edit(fruit: Ingredient, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove fruit if it no longer has a name
    if (!value) {
      this.remove(fruit);
      return;
    }

    // Edit existing fruit
    const index = this.ingredientsAsArray.indexOf(fruit);
    if (index > 0) {
      this.ingredientsAsArray[index].name = value;
    }
  }

  updateQuery(){
    this.selectedIngredients='';

    for (let ingredient of this.ingredientsAsArray){
      if(this.ingredientsAsArray.indexOf(ingredient) > 0 ){
        this.selectedIngredients = this.selectedIngredients.concat(','+ingredient.name);
      }
      else{
        this.selectedIngredients = ingredient.name;
      }
    }
    console.log(this.selectedIngredients);
    //this.selectedIngredients = 'Tomato,Chicken';
    this.queryParams = this.queryParams.delete('ingredientNames');
    this.query();
  }

}

interface Category {
  backendValue: string;
  text: string;
}





@Component({
  selector: 'recipe-filter-dialog',
  templateUrl: 'recipe-filter-dialog.html',
})
export class RecipeFilterDialog implements OnInit{

  public queryParams = new HttpParams();

  tags: String[] = [];

  categories: Category[] = [
    {backendValue: 'MAINCOURSE', text: 'Main Course'},
    {backendValue: 'DESSERT', text: 'Dessert'},
    {backendValue: 'SIDE', text: 'Side dish'},
    {backendValue: 'STARTER', text: 'Starter'},
    {backendValue: 'DRINKS', text: 'Drinks'},
    {backendValue: 'BREAKFAST', text: 'Breakfast'},
  ];

  constructor(
    public dialogRef: MatDialogRef<RecipeFilterDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private _recipeService: RecipeService) {

    }

  ngOnInit(): void {
    this._recipeService.getTags()
    .subscribe(data => this.tags = data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

export interface DialogData {
  categories: Category[],
  selectedCategory: string;
  tags: String[],
  selectedTagsAsArray: String[]
}


export interface Ingredient {
  name: string;
}

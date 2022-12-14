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
  @Output() public newSortEvent = new EventEmitter();

  public queryParams = new HttpParams();

  selectedCategory: string;

  tags: string[];
  selectedTagsAsArray: string[];
  selectedTags: string;

  ingredientsAsArray: Ingredient[] = [];
  selectedIngredients: string;

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;


  constructor(public dialog: MatDialog) {
    this.selectedCategory = "";
    this.selectedIngredients = "";
    this.selectedTags = "";
    this.selectedTagsAsArray = [];
    this.tags = [];
  }


  openDialog(): void {
    const dialogRef = this.dialog.open(RecipeFilterDialog, {
      width: '900px',
      data: {
        selectedCategory: this.selectedCategory,
        selectedTagsAsArray: this.selectedTagsAsArray
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.selectedCategory = result.selectedCategory;
      this.selectedTagsAsArray = result.selectedTagsAsArray;

      if (!this.selectedTagsAsArray.length){
        this.selectedTags = "";
      }
      else{
        this.selectedTagsAsArray.forEach((value: string, index: number) => {
          if (index == 0){
            this.selectedTags = value;
          }
          else {
            this.selectedTags = this.selectedTags.concat("," + value)
          }
        });
      }


      this.queryParams = this.queryParams.delete('category');
      this.queryParams = this.queryParams.delete('tags');

      this.query();
    });
  }

  query(){
    if (this.selectedCategory){
      this.queryParams = this.queryParams.append("category",this.selectedCategory);
    }

    if (this.selectedTags){
      this.queryParams = this.queryParams.append("tags",this.selectedTags);
    }

    if (this.selectedIngredients){
      this.queryParams = this.queryParams.append("ingredientNames",this.selectedIngredients);
    }

    this.newQueryEvent.emit(this.queryParams);
  }



  //METHODS FOR THE INGREDIENTS FILTERING
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.ingredientsAsArray.push({name: value});
      this.prepIngredientsQuery();
      this.query();
    }

    event.chipInput!.clear();
  }

  remove(ingredient: Ingredient): void {
    const index = this.ingredientsAsArray.indexOf(ingredient);

    if (index >= 0) {
      this.ingredientsAsArray.splice(index, 1);
      this.prepIngredientsQuery();
      this.query();
    }
  }

  prepIngredientsQuery(){
    this.selectedIngredients='';

    for (let ingredient of this.ingredientsAsArray){
      if(this.ingredientsAsArray.indexOf(ingredient) > 0 ){
        this.selectedIngredients = this.selectedIngredients.concat(','+ingredient.name);
      }
      else{
        this.selectedIngredients = ingredient.name;
      }
    }
    this.queryParams = this.queryParams.delete('ingredientNames');
  }


  //METHODS FOR THE SORT BUTTON
  sortByName(startWith: String): void {
    this.newSortEvent.emit("name"+startWith);
  }
}

interface Category {
  backendValue: string;
  text: string;
}
export interface Ingredient {
  name: string;
}


@Component({
  selector: 'recipe-filter-dialog',
  templateUrl: 'recipe-filter-dialog.html',
})
export class RecipeFilterDialog implements OnInit{

  public queryParams = new HttpParams();

  tags: String[] = [];
  selectedCategory: String;
  selectedTagsAsArray: String[];

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
    console.log(this.data)
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  categoryWasSelected(categoryToCheck: String, previousSelectedCategory: String): boolean {
    if (categoryToCheck == previousSelectedCategory){
        return true;
    }

    return false;
  }

  tagWasSelected(tagToCheck: String, tagsAsArray: String[]): boolean {
    for (let tag of tagsAsArray){
      if (tag == tagToCheck){
        return true;
      }
    }
    return false;
  }

}

export interface DialogData {
  categories: Category[],
  selectedCategory: string;
  tags: String[],
  selectedTagsAsArray: String[]
}




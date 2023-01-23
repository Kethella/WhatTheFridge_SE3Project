import { Component, Output, EventEmitter, Inject, OnInit, LOCALE_ID } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatChipEditedEvent, MatChipInputEvent} from '@angular/material/chips';
import {ENTER, COMMA} from '@angular/cdk/keycodes';
import { RecipeService } from 'src/app/services/recipe.service';
import { ICategory } from 'src/app/models/category';
import { FormBuilder, FormGroup,FormControl,Validators, NonNullableFormBuilder } from '@angular/forms';
import { firstValueFrom } from 'rxjs';




@Component({
  selector: 'app-recipe-sort-filter',
  templateUrl: './recipe-sort-filter.component.html',
  styleUrls: ['./recipe-sort-filter.component.css']
})
export class RecipeSortFilterComponent{

  @Output() public newQueryEvent = new EventEmitter<HttpParams>();
  @Output() public newSortEvent = new EventEmitter();

  public queryParams = new HttpParams();

  selectedCategory: ICategory;

  tags: string[];
  selectedTagsAsArray: string[];
  selectedTags: string;

  ingredientsAsArray: Ingredient[] = [];
  selectedIngredients: string;

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;


  constructor(public dialog: MatDialog) {
    this.selectedIngredients = "";
    this.selectedTags = "";
    this.selectedTagsAsArray = [];
    this.tags = [];
    this.selectedCategory = {text: "", enumValue: ""};
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


      if (result?.selectedCategory.enumValue != NonNullableFormBuilder) {
        this.selectedCategory = result.selectedCategory;
      }

      if(result?.selectedTagsAsArray != null) {
        this.selectedTagsAsArray = result.selectedTagsAsArray;
      }


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




      this.query();
    });
  }

  query(){
    this.queryParams = this.queryParams.delete('category');
      this.queryParams = this.queryParams.delete('tags');



    if (this.selectedCategory && this.selectedCategory.text != ""){
      this.queryParams = this.queryParams.append("category", this.selectedCategory.enumValue);
    }

    if (this.selectedTags){
      this.queryParams = this.queryParams.append("tags", this.selectedTags);
    }

    if (this.selectedIngredients){
      this.queryParams = this.queryParams.append("ingredientNames", this.selectedIngredients);
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

export interface Ingredient {
  name: string;
}


@Component({
  selector: 'recipe-filter-dialog',
  templateUrl: 'recipe-filter-dialog.html',
  styleUrls: ['./recipe-filter-dialog.css']
})
export class RecipeFilterDialog implements OnInit{

  public queryParams = new HttpParams();

  tags: String[] = [];
  selectedTagsAsArray: String[];
  selectedCategory: ICategory = {"enumValue": "", "text":""};
  categories: ICategory[];

  constructor(
    public dialogRef: MatDialogRef<RecipeFilterDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private _recipeService: RecipeService) {
      this.selectedCategory = data.selectedCategory;

  }

  async ngOnInit() {
    this.tags = await this._recipeService.getTags();
    this.categories = await this._recipeService.getCategories();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onCloseClick(categories: ICategory[], selectedCategory: ICategory): void {
    if(selectedCategory.text != undefined){
      this.data.selectedCategory = this.setSelectedCategory(categories, selectedCategory)
    }
    else {
      this.selectedCategory.text = ""
      this.selectedCategory.enumValue = ""
    }
    this.dialogRef.close(this.data)
  }

  tagWasSelected(tagToCheck: String, tagsAsArray: String[]): boolean {
    for (let tag of tagsAsArray){
      if (tag == tagToCheck){
        return true;
      }
    }
    return false;
  }

  setSelectedCategory(categories: ICategory[], selectedCategory: ICategory): ICategory{

    for (let category of categories){
      if (selectedCategory.text == category.text){
        selectedCategory.enumValue = category.enumValue;
      }
    }

    return selectedCategory;
  }


}

export interface DialogData {
  selectedCategory: ICategory;
  selectedTagsAsArray: String[]
}




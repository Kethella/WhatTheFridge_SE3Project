import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { HttpEventType, HttpParams, HttpResponse } from '@angular/common/http';
import { Component, ViewChild, Inject, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ICategory } from 'src/app/models/category';
import { Recipe } from 'src/app/models/recipe';
import { MediaService } from 'src/app/services/media.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { MatSnackBar} from '@angular/material/snack-bar';


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

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  selectedFile = null;
  mediaString: string = "";

  fileIsSelected = false;


  @ViewChild(MatTable) table: MatTable<Ingredient>;
  ingredient: Ingredient = {
    "ingredientName": '',
    "ingredientAmount": ''
  }

  constructor(public dialogRef:MatDialogRef<EditRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService,
    private _mediaService: MediaService,
    public dialog: MatDialog,
    private snackBar:MatSnackBar){

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

  onCancel(){
    this.dialogRef.close();
  }

  async updateRecipe(){

    this.selectedCategory = this.setSelectedCategory(this.selectedCategory)
    this.recipe.category = this.selectedCategory.enumValue

    this.recipe.ingredientNames = this.ingredientNames;
    this.recipe.ingredientMeasures = this.ingredientAmounts;

    this.recipe = await this._recipeService.updateRecipe(this.recipe);
    this.dialogRef.close();
 }

  onSubmit(){
    if(this.emptyMandatoryFields()){
      if(this.fileIsSelected) {
        this.progress.percentage = 0;
        this.currentFileUpload = this.selectedFiles.item(0)!;
        this._mediaService.uploadFile(this.currentFileUpload).subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total!);
          } else if (event instanceof HttpResponse) {
            this.mediaString = JSON.stringify(event.body)
            this.mediaString = this.mediaString.slice(1, this.mediaString.length - 1)

            // a bit ugly but i cannot get the mediaString out of the subscribe :/
            this.recipe.image = this.mediaString;
            this.updateRecipe();
          }
          this.selectedFiles = undefined!;
          }
        );
      }
      else {
        this.updateRecipe()
      }
    }
    else{
      this.snackBar.open("Make sure that all mandatory fields are not empty.", "Ok", {
        duration: 5000,
        panelClass: ['my-snackbar']
      });
    }

  }


selectFile(event) {
  this.fileIsSelected = true;
  this.selectedFiles = event.target.files;
}

deleteImage() {
  this._mediaService.deleteFile(this.recipe.image)
  this.recipe.image = "";
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

  onAddIngredient(){
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

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogChangeImage, {
      data: {
        recipe: this.recipe
      },
    });
  }



  /*  --------------------------------------------------------------------------------
        ERROR HANDLING
      --------------------------------------------------------------------------------
  */
  emptyMandatoryFields(): boolean {
    if(this.recipe.name === "" || this.recipe.name === " "){

      return false;
    }
    if(this.recipe.ingredientNames.length===0){

      return false;
    }
    if(this.recipe.ingredientMeasures.length===0 ){

      return false;
    }
    if(this.recipe.instructions==="" || this.recipe.instructions===null){

      return false;
    }
    if(this.recipe.category=="" ||this.recipe.category==null){
      return false;
    }

    return true;
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

@Component({
  selector: 'dialog-change-image',
  templateUrl: 'dialog-change-image.html',
})
export class DialogChangeImage{

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  selectedFile = null;
  mediaString: string = "";

  fileIsSelected = false;


  constructor(
    public dialogRef: MatDialogRef<DialogChangeImage>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private _mediaService: MediaService
  ) {}

  onCancelClick(): void {
    this.dialogRef.close();
  }

  selectFile(event) {
    this.fileIsSelected = true;
    this.selectedFiles = event.target.files;
  }

  changeImage() {
    if(this.fileIsSelected) {
      this.progress.percentage = 0;
      this.currentFileUpload = this.selectedFiles.item(0)!;
      this._mediaService.uploadFile(this.currentFileUpload).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress.percentage = Math.round(100 * event.loaded / event.total!);
        } else if (event instanceof HttpResponse) {
          this.mediaString = JSON.stringify(event.body)
          this.mediaString = this.mediaString.slice(1, this.mediaString.length - 1)

          // a bit ugly but i cannot get the mediaString out of the subscribe :/
          this.data.recipe.image = this.mediaString;
        }
        this.selectedFiles = undefined!;
        }
      );
    }

    this.onCancelClick();
  }

}

export interface DialogData {
  recipe: Recipe
}

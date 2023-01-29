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
import { HttpClient, HttpEventType, HttpParams, HttpResponse } from '@angular/common/http';
import { MediaService } from 'src/app/services/media.service';


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

  mediaString: string = "";

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  selectedFile = null;

  fileIsSelected = false;

  public queryParams = new HttpParams();
  @Output() public newQueryEvent = new EventEmitter<HttpParams>();

  @ViewChild(MatTable) table: MatTable<Ingredient>;

  constructor(public dialogRef:MatDialogRef<CreateRecipeComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any,
    private _formBuilder: FormBuilder,
    private _recipeService:RecipeService,
    private _mediaService: MediaService,
    public http: HttpClient){

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


  selectFile(event) {
    this.fileIsSelected = true;
    this.selectedFiles = event.target.files;
  }

  onSubmit(){

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
            this.selectedCategory = this.setSelectedCategory(this.selectedCategory)
            this.recipe={
              id: '',
              name:this.recipeForm.get('name')?.value,
              ingredientNames: this.ingredienNames,
              ingredientMeasures: this.ingredientAmounts,
              category: this.selectedCategory.enumValue,
              tags: this.selectedTags,
              instructions:this.recipeForm.get('instructions')?.value,
              image: this.mediaString,
              link:'',
              ownerAccount:''
            }
            this.createRecipe()
        }
        this.selectedFiles = undefined!;
        }
      );
    } else{
      this.selectedCategory = this.setSelectedCategory(this.selectedCategory)
        this.recipe={
          id: '',
          name:this.recipeForm.get('name')?.value,
          ingredientNames: this.ingredienNames,
          ingredientMeasures: this.ingredientAmounts,
          category: this.selectedCategory.enumValue,
          tags: this.selectedTags,
          instructions:this.recipeForm.get('instructions')?.value,
          image: "",
          link:'',
          ownerAccount:''
        }
        this.createRecipe()
    }

 }


 async createRecipe() {
  this.recipe = await this._recipeService.createRecipe(this.recipe);
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

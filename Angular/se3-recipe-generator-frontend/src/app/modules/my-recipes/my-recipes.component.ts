import { HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RecipeService } from 'src/app/services/recipe.service';
import { CreateRecipeComponent } from './create-recipe/create-recipe.component';


@Component({
  selector: 'app-my-recipes',
  templateUrl: './my-recipes.component.html',
  styleUrls: ['./my-recipes.component.css']
})
export class MyRecipesComponent {

  public recipes = [] as any;
  public queryParams = new HttpParams();
  public ingredients: string;

  makeNoItemsElementVisible = true;

  constructor(private _recipeService: RecipeService, private dialog:MatDialog){

  }

  ngOnInit(): void {
    this.loadRecipes()
  }

  async loadRecipes() {
    this.queryParams = this.queryParams.append("defaultRecipes", "no")
    this.recipes = await this._recipeService.getRecipes(this.queryParams);

    if(this.recipes){
      this.makeNoItemsElementVisible = false;
    }
    else{
      this.makeNoItemsElementVisible = true;
    }
  }

  openDialog(){
    const dialogRef=this.dialog.open(CreateRecipeComponent, {
      data: this.ingredients,
      width: '900px',
    })
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }
}

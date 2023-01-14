import { HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';


@Component({
  selector: 'app-my-recipes',
  templateUrl: './my-recipes.component.html',
  styleUrls: ['./my-recipes.component.css']
})
export class MyRecipesComponent {

  public recipes = [] as any;
  public queryParams = new HttpParams();

  constructor(private _recipeService: RecipeService){

  }

  ngOnInit(): void {
    this.loadRecipes()
  }

  async loadRecipes() {
    this.queryParams = this.queryParams.append("defaultRecipes", "no")
    this.recipes = await this._recipeService.getRecipes(this.queryParams);
  }
}

import { Component } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { RecipeService } from 'src/app/services/recipe.service';
import { Recipe } from 'src/app/models/recipe';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  public recipes = [] as any;
  public errorMsg = "" as any;
  public queryParams = new HttpParams();

  constructor(private _recipeService: RecipeService) {
  }

  async ngOnInit() {
    this.recipes = await this._recipeService.getRecipes(this.queryParams)
    this.recipes = this.recipes.sort((a: Recipe, b: Recipe) => a.name.localeCompare(b.name));
  }

  async restart(queryParams: HttpParams) {
    this.recipes = await this._recipeService.getRecipes(queryParams);
    this.recipes = this.recipes.sort((a: Recipe, b: Recipe) => a.name.localeCompare(b.name));
  }

  sort(criteria: string): void {
    if(criteria == "nameA"){
      this.recipes = this.recipes.sort((a: Recipe, b: Recipe) => a.name.localeCompare(b.name));
    }
    if(criteria == "nameZ"){
      this.recipes = this.recipes.sort((a: Recipe, b: Recipe) => a.name.localeCompare(b.name));
      this.recipes = this.recipes.reverse();
    }
  }
}

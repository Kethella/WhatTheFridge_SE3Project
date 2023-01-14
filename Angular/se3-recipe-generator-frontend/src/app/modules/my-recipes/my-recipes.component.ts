import { Component } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';
@Component({
  selector: 'app-my-recipes',
  templateUrl: './my-recipes.component.html',
  styleUrls: ['./my-recipes.component.css']
})
export class MyRecipesComponent {

  public recipes = [] as any;
  constructor(private _recipeService: RecipeService){

  }
}

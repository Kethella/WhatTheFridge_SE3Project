import { Component } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  public recipes = [] as any;

  public errorMsg = "" as any;
  public breakpoint: number = 6;

  public queryParams = new HttpParams();

  constructor(private _recipeService: RecipeService) {
  }

  ngOnInit(): void {
    this._recipeService.getRecipes(this.queryParams)
        .subscribe(data => this.recipes = data,
                    error => this.errorMsg = error);
    console.log(this.recipes);
  }

  restart(queryParams: HttpParams): void {
    this._recipeService.getRecipes(queryParams)
        .subscribe(data => this.recipes = data);
  }
}

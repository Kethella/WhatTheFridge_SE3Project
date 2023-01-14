import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError, Subject, firstValueFrom } from 'rxjs';
import { ICategory } from '../models/category';
import { Recipe } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  data = {};
  private _baseUriRecipe: string = "http://localhost:8085/api/v1/recipes/oa=2238550034095900/"
  private _baseUriTags: string = "http://localhost:8085/api/v1/recipes/tags/oa=2238550034095900/"

  constructor(private http: HttpClient) { }

  async getRecipes(queryParams: HttpParams): Promise<Recipe[]> {
    if (queryParams) {
      return firstValueFrom(this.http.get<Recipe[]>(this._baseUriRecipe, {params:queryParams}));
    }
    else {
      return firstValueFrom(this.http.get<Recipe[]>(this._baseUriRecipe));
    }
  }

  async getTags(): Promise<String[]> {
    return firstValueFrom(this.http.get<String[]>(this._baseUriTags));
  }

  async getCategories(): Promise<ICategory[]> {
    return firstValueFrom(this.http.get<ICategory[]>("http://localhost:8085/api/v1/categories"))
  }

  public save(recipe: Recipe) {
    return this.http.post<Recipe>(this._baseUriRecipe, recipe);
  }

}

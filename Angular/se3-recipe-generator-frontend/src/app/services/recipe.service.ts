import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError, Subject, firstValueFrom } from 'rxjs';
import { IRecipe } from '../models/recipe';
import { ICategory } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  data = {};
  private _baseUriRecipe: string = "http://localhost:8085/api/v1/recipes/oa=1310140241453400/"
  private _baseUriTags: string = "http://localhost:8085/api/v1/recipes/tags/oa=1310140241453400/"

  constructor(private http: HttpClient) { }

  async getRecipes(queryParams: HttpParams): Promise<IRecipe[]> {
    if (queryParams) {
      return firstValueFrom(this.http.get<IRecipe[]>(this._baseUriRecipe, {params:queryParams}));
    }
    else {
      return firstValueFrom(this.http.get<IRecipe[]>(this._baseUriRecipe));
    }
  }

  async getTags(): Promise<String[]> {
    return firstValueFrom(this.http.get<String[]>(this._baseUriTags));
  }

  async getCategories(): Promise<ICategory[]> {
    return firstValueFrom(this.http.get<ICategory[]>("http://localhost:8085/api/v1/categories"))
  }


}

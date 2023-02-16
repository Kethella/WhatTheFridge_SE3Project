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

  private ownerAccount: string = "2238550034095900"; //todo: change back to empty string
  private _baseUri: string = "http://localhost:8085/api/v1/recipes"

  constructor(private http: HttpClient) { }

  async getRecipes(queryParams: HttpParams): Promise<Recipe[]> {
    if (queryParams) {
      return firstValueFrom(this.http.get<Recipe[]>(`${this._baseUri}/oa=${this.ownerAccount}/`, {params:queryParams}));
    }
    else {
      return firstValueFrom(this.http.get<Recipe[]>(`${this._baseUri}/oa=${this.ownerAccount}/`));
    }
  }

  async getTags(): Promise<String[]> {
    return firstValueFrom(this.http.get<String[]>(`${this._baseUri}/tags/oa=${this.ownerAccount}`));
  }

  async getCategories(): Promise<ICategory[]> {
    return firstValueFrom(this.http.get<ICategory[]>("http://localhost:8085/api/v1/recipes/categories"))
  }

  async createRecipe(recipe: Recipe): Promise<Recipe> {
    recipe.ownerAccount = this.ownerAccount;
    return firstValueFrom(this.http.post<Recipe>(`${this._baseUri}`, recipe));
  }

  setOwnerAccount(oa: string): void{
    this.ownerAccount = oa;
  }

  async deleteRecipe(recipe: Recipe): Promise<any> {
    return firstValueFrom(this.http.delete(`${this._baseUri}/${recipe.id}`));
  }

  async updateRecipe(updatedRecipe: Recipe):Promise<any>{
    updatedRecipe.ownerAccount = this.ownerAccount;
    return firstValueFrom(this.http.put(`${this._baseUri}/${updatedRecipe.id}`, updatedRecipe))
  }

}

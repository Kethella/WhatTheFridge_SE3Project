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

  private ownerAccount: string = "";
  private _baseUri: string = "http://localhost:8085/api/v1/recipes"

  constructor(private http: HttpClient) { }

  async getRecipes(queryParams: HttpParams): Promise<Recipe[]> {
    console.log(this.ownerAccount)
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
    return firstValueFrom(this.http.get<ICategory[]>("http://localhost:8085/api/v1/categories"))
  }

  public save(recipe: Recipe) {
    return this.http.post<Recipe>(`${this._baseUri}/oa=${this.ownerAccount}`, recipe);
  }

  setOwnerAccount(oa: string): void{
    this.ownerAccount = oa;
  }

}
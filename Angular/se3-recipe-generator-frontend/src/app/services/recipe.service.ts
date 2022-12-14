import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError, Subject } from 'rxjs';
import { IRecipe } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  data = {};
  private _baseUriRecipe: string = "http://localhost:8085/api/v1/recipes/oa=1845400701742500/"
  private _baseUriTags: string = "http://localhost:8085/api/v1/recipes/tags/oa=1845400701742500/"

  constructor(private http: HttpClient) { }

  getRecipes(queryParams: HttpParams): Observable<IRecipe[]> {
    if (queryParams) {
      return this.http.get<IRecipe[]>(this._baseUriRecipe, {params:queryParams})
      .pipe(catchError(this.errorHandler));
    }
    else {
      return this.http.get<IRecipe[]>(this._baseUriRecipe)
      .pipe(catchError(this.errorHandler));
    }

  }

  getTags(): Observable<String[]> {

    return this.http.get<String[]>(this._baseUriTags)
    .pipe(catchError(this.errorHandler));

  }

  private errorHandler(error: HttpErrorResponse){
    return throwError(error.message || "Server Error")
  }


}

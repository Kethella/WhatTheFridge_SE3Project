import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recipe } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private recipesUrl: string;

  constructor(private http: HttpClient) {
    this.recipesUrl = 'http://localhost:8085/api/v1/recipes';
  }

  public findAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl);
  }

  public save(recipe: Recipe) {
    return this.http.post<Recipe>(this.recipesUrl, recipe);
  }
}

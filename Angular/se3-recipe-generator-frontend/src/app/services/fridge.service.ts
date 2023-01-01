import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { FridgeItem } from '../models/fridgeItem';
import { Recipe } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class FridgeService {

  private _baseUri: string = "http://localhost:8085/api/v1/fridgeItems";

  constructor(private http: HttpClient) { }

  async getFridgeItems(): Promise<FridgeItem[]> {
    return firstValueFrom(this.http.get<FridgeItem[]>(this._baseUri));
  }

  public saveItem(fridgeItem: FridgeItem) {
    return this.http.post<Recipe>(this._baseUri, fridgeItem);
  }
}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { FridgeItem } from '../models/fridgeItem';

@Injectable({
  providedIn: 'root'
})
export class FridgeService {

  private _baseUri: string = "http://localhost:8085/api/v1/fridgeItems";

  constructor(private http: HttpClient) { }

  async getFridgeItems(): Promise<FridgeItem[]> {
    return firstValueFrom(this.http.get<FridgeItem[]>(this._baseUri));
  }

  async saveItem(fridgeItem: FridgeItem): Promise<FridgeItem>  {
    return firstValueFrom(this.http.post<FridgeItem>(this._baseUri, fridgeItem));
  }

  async deleteItem(fridgeItem: FridgeItem): Promise<any> {
    return firstValueFrom(this.http.delete(`${this._baseUri}/${fridgeItem.id}`));
  }
}

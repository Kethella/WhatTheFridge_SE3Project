import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { FridgeItem } from '../models/fridgeItem';

@Injectable({
  providedIn: 'root'
})
export class FridgeService {
  private ownerAccount: string = "2238550034095900" //todo: change back to empty string
  private _baseUri: string = "http://localhost:8085/api/v1/fridgeItems";

  private fridgeItems: FridgeItem[] = [];
  private expiringFridgeItems: NotifItem[];

  constructor(private http: HttpClient) { }

  async getFridgeItems(): Promise<FridgeItem[]> {
    return firstValueFrom(this.http.get<FridgeItem[]>(`${this._baseUri}/oa=${this.ownerAccount}`));
  }

  async saveItem(fridgeItem: FridgeItem): Promise<FridgeItem>  {
    fridgeItem.ownerAccount = this.ownerAccount;
    return firstValueFrom(this.http.post<FridgeItem>(this._baseUri, fridgeItem));
  }

  async updateItem(fridgeItem: FridgeItem): Promise<any> {
    return firstValueFrom(this.http.put(`${this._baseUri}/${fridgeItem.id}`, fridgeItem));
  }

  async deleteItem(fridgeItem: FridgeItem): Promise<any> {
    return firstValueFrom(this.http.delete(`${this._baseUri}/${fridgeItem.id}`));
  }

  setOwnerAccount(oa: string): void{
   // this.ownerAccount = oa;
  }




  async getUpdatedNotifications(): Promise<NotifItem[]> {
    this.fridgeItems = await this.getFridgeItems()
    this.expiringFridgeItems = [];

    for (var item of this.fridgeItems) {
      var fridgeItem = <FridgeItem> item;
      const itemStatus = this.getExpirationSatus(fridgeItem.expirationDate);
      if (itemStatus === "expires soon" || itemStatus === "expired") {
        const tempItem: NotifItem = {
          item: item,
          status: itemStatus
        }
        this.expiringFridgeItems.push(tempItem);
      }
    }
    return this.expiringFridgeItems
  }

  getExpirationSatus(dateToCheck: string): string {
    const today = Date.now();
    const itemDate = Date.parse(dateToCheck);

    if (itemDate < today) {
      return "expired"
    }
    else if (itemDate > today + 3 * 24 * 60 * 60 * 1000) {
      return "ok"
    }
    else {
      return "expires soon"
    }
  }
}

export interface NotifItem {
  item: FridgeItem,
  status: string
}


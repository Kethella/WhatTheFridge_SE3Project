import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Account } from '../models/account'
import { ISecurityQuestion } from '../models/securityQuestions';
import { firstValueFrom } from 'rxjs';
import { RecipeService } from './recipe.service';
import { FridgeService } from './fridge.service';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private ownerAccountId: string = "" //todo: change back to empty string
  private _baseUrl = "http://localhost:8085/api/v1/accounts";


  constructor(private http: HttpClient,
    private _fridgeService: FridgeService,
    private _recipeService: RecipeService) { }

  async createAccount(account: Account): Promise<Account> {
    return firstValueFrom(this.http.post<Account>(this._baseUrl, account));
  }

  async findAccount(queryParams: HttpParams): Promise<Account> {
    return firstValueFrom(this.http.get<Account>("http://localhost:8085/api/v1/accounts/one/", {params:queryParams}));
  }

  async getSecurityQuestions(): Promise<ISecurityQuestion[]> {
    return firstValueFrom(this.http.get<ISecurityQuestion[]>("http://localhost:8085/api/v1/securityQuestions"));
  }

  sendOwnerAccountToServices(oa: string): void {
    this.ownerAccountId = oa;
    this._fridgeService.setOwnerAccount(oa);
    this._recipeService.setOwnerAccount(oa);
  }


  async getAccountInfo(): Promise<Account> {
    return firstValueFrom(this.http.get<Account>(`${this._baseUrl}/${this.ownerAccountId}`))
  }

  async updateAccountInfo(account: Account): Promise<any> {
    return firstValueFrom(this.http.put(`${this._baseUrl}/${this.ownerAccountId}`, account))
  }

  async deleteAccount(): Promise<any> {
    return firstValueFrom(this.http.delete(`${this._baseUrl}/${this.ownerAccountId}`))
  }
}

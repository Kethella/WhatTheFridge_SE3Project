import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account'
import { ISecurityQuestion } from '../models/securityQuestions';
import { firstValueFrom } from 'rxjs';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _baseUrl = "http://localhost:8085/api/v1/accounts";


  constructor(private http: HttpClient) { }

  async createAccount(account: Account): Promise<Account> {
    return firstValueFrom(this.http.post<Account>(this._baseUrl, account));
  }


  async getSecurityQuestions(): Promise<ISecurityQuestion[]> {
    return firstValueFrom(this.http.get<ISecurityQuestion[]>("http://localhost:8085/api/v1/securityQuestions"));
  }
}


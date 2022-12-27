import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account'
import { ISecurityQuestion } from '../models/securityQuestions';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _baseUrl = "http://localhost:8085/api/v1/accounts";


  constructor(private http: HttpClient) { }

  createAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${this._baseUrl}`, account);
  }

  getSecurityQuestions(): Observable<ISecurityQuestion[]> {
    return this.http.get<ISecurityQuestion[]>("http://localhost:8085/api/v1/securityQuestions")
  }
}


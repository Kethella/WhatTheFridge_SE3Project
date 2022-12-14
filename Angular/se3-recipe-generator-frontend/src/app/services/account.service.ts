import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account'
import { map } from 'rxjs';
import { Token } from '@angular/compiler';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _baseUrl = "http://localhost:8085/api/v1/accounts";


  constructor(private http: HttpClient, private httpHeaders:HttpHeaders) { }

    /*getAll():Observable<Account[]> {
        return this.http.get<Account[]>(this.url)
    }

    login(model: any){
        return this.http.post(this.url + 'login', model).pipe(
            map((response: any) => {
                const user = response;
                if (user.result.succeeded){
                    localStorage.setItem('token', user.token)
                }
            })
        )
    }*/

  login(username: string, password: string):Observable<any> {
        return this.http.post(this._baseUrl + 'login', { username, password }, httpOptions);
  }

  register(username:string, password:string, email:string ): Observable<any>{
        return this.http.post(this._baseUrl + 'signup', {username, password, email}, httpOptions);
  }


  getAll():Observable<Account[]> {
        return this.http.get<Account[]>(this._baseUrl)
  }

  createAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${this._baseUrl}`, account);
  }
}


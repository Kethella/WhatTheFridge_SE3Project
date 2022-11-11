import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account'

@Injectable({ providedIn: 'root' })

export class AccountService { 

    private url = "http://localhost:8085/api/v1/accounts";

    constructor(private http: HttpClient) { }

    getAll():Observable<Account[]> {
        return this.http.get<Account[]>(this.url)
    }
    
}
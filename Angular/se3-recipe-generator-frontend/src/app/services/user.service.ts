import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user'

@Injectable({ providedIn: 'root' })

export class UserService { 

    private url = "http://localhost:8085/api/v1/accounts";

    constructor(private http: HttpClient) { }

    getAll():Observable<User[]> {
        return this.http.get<User[]>(this.url)
    }
    
}
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private router: Router) { }

  public isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  public login(): void {
    this.loggedIn.next(true);
    this.router.navigate(["/home"])
  }

  public logout(): void {
    this.loggedIn.next(false);
    this.router.navigate(["/login"])
  }

  public isLoggedInValue(): boolean {
    return this.loggedIn.value;
  }
}

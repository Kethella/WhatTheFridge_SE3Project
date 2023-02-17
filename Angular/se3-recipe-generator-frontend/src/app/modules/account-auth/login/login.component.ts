import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  thereIsInputError: boolean = false;

  public queryParams = new HttpParams();

  public account: Account = {
    id: "",
    name: "",
    email: "",
    password: "",
    securityQuestion: "",
    securityAnswer: ""
  }


  constructor(private _accountService: AccountService,
    private formBuilder: FormBuilder,
    private http:HttpClient,
    private router:Router,
    private snackBar: MatSnackBar ) { }

  ngOnInit(): void {
  }

  async login(){
    if(!this.fieldsAreEmpty()){

      this.queryParams = this.queryParams.append("email", this.account.email);
      this.queryParams = this.queryParams.append("password", this.account.password);
      const foundAccount = await this._accountService.findAccount(this.queryParams);

      if(foundAccount === null){
        this.thereIsInputError = true;
      }
      else {
        this.account = foundAccount;
        this._accountService.sendOwnerAccountToServices(this.account.id);
        this.router.navigate(['home']);
      }
    }
    else{
      this.thereIsInputError = true;
    }
  }

  fieldsAreEmpty(): boolean {
    if(this.account.password === "" || this.account.email === ""){
      return true;
    }
    return false;
  }



  }



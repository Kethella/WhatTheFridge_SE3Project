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
  
  submitted= false;
  public loginForm: FormGroup = new FormGroup({
    email: new FormControl(),
    password: new FormControl()
  });
  
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
    this.loginForm = this.formBuilder.group({
      email :[Validators.required, Validators.email, ''],
      password: [Validators.required, ''],
    })
  }


  get f(): { [key: string]: AbstractControl } {
    return this.loginForm.controls;
  }

  async login(){
    /*if(this.everythingIsFine()){
     
      this.queryParams = this.queryParams.append("email", this.account.email);
      this.queryParams = this.queryParams.append("password", this.account.password);
      this.account = await this._accountService.findAccount(this.queryParams);
      
      console.log(this.account)
      
      if(this.account.id == ""){
        alert("User not found. Please check again your email and password.")
      }
      else {
        if(this.loginForm.invalid){
          return;
        }
        this.loginForm.reset();
        this._accountService.sendOwnerAccountToServices(this.account.id);
        this.router.navigate(['home']);
      }
    }
    else{
      console.log("bad bitch")
      let config = new MatSnackBarConfig();
      config.panelClass = ['my-snackbar']
      this.snackBar.open("Make sure there are no empty fields.", "Ok", config);

    }*/

    this.submitted=true;
    if(this.loginForm.invalid){
      return;
    }

    this.queryParams = this.queryParams.append("email", this.account.email);
      this.queryParams = this.queryParams.append("password", this.account.password);
      this.account = await this._accountService.findAccount(this.queryParams);
      if(this.account.id=="" || this.account.id==null){
        let config = new MatSnackBarConfig();
        config.panelClass = ['my-snackbar']
        this.snackBar.open("Wrong username and/or password.", "Ok", config);
      }
      else{
        this.loginForm.reset();
        this._accountService.sendOwnerAccountToServices(this.account.id);
        this.router.navigate(['home']);
      }
      
    /*this.http.get<any>("http://localhost:8085/api/v1/accounts")
    .subscribe(res =>{
      let user = res.find((a:Account)=>{
        return a.email === this.loginForm.value.email && a.password === this.loginForm.value.password
      }
      )
      console.log(res)
      if(user){
        alert("Login success")
        console.log(user.id);
        this._accountService.sendOwnerAccountToServices(user.id);
        this.router.navigate(['home']);
      } else{
        alert("User not found")
      }
    },
    err=>{
      alert("Something went wrong !!")

    })*/
  }

  everythingIsFine(): boolean {
    if(this.account.name === ""){

      return false;
    }
    if(this.account.email === ""){

      return false;
    }
    return true;
  }



  }



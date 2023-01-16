import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm!: FormGroup;
  public queryParams = new HttpParams();
  public account: Account = {
    "id": "",
    "name": "",
    "email": "",
    "password": "",
    "securityQuestion": "",
    "securityAnswer": ""
  }

  constructor(private _accountService: AccountService,
    private formBuilder: FormBuilder,
    private http:HttpClient,
    private router:Router ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email:[''],
      password:[''],
    })
  }


  /*onLogin(){
      this.http.get<any>("http://localhost:8085/api/v1/accounts")
      .subscribe(res=>{
        const user = res.find((a:any)=>{
          return a.email === this.loginForm.value.email && a.password === this.loginForm.value.password
        });
        if(user){
          alert('Login Succesful');
          this.loginForm.reset()
        this.router.navigate(["home"])
        }else{
          alert("user not found")
        }
      },err=>{
        alert("Something went wrong")
      })
  }*/

  async login(){
    this.queryParams = this.queryParams.append("email", this.account.email);
    this.queryParams = this.queryParams.append("password", this.account.password);
    this.account = await this._accountService.findAccount(this.queryParams);

    console.log(this.account)

    if(this.account.id == ""){
      alert("User not found. Please check again your email and password.")
    }
    else {
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


  }



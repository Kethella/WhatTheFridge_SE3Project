import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm!: FormGroup;
  constructor(private accountService: AccountService, private formBuilder: FormBuilder, private http:HttpClient, private router:Router ) { }

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

  login(){
    this.http.get<any>("http://localhost:8085/api/v1/accounts")
    .subscribe(res =>{
      const user = res.find((a:any)=>{
        return a.email === this.loginForm.value.email && a.password === this.loginForm.value.password
      }
      )
      console.log(res)
      if(user){
      alert("Login success")
      this.loginForm.reset();
      this.router.navigate(['home']);
      } else{
        alert("User not found")
      }
    },
    err=>{
      alert("Something went wrong !!")
    
    })
  }
  }



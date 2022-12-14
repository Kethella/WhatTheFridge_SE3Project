import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  //isLinear = false;
  firstFormGroup: FormGroup;
  //secondFormGroup: FormGroup;
  //signUpForm: FormGroup;
  signUpSuccessful: boolean;
  form:any={username: null, email:null, password:null }


  account: Account = {
    name: "Alex",
    email: "alex@gmail.com",
    password: "1234",
    securityQuestion: "Q1",
    securityAnswer: "blabla"
  }

  public account2: Account;

  constructor(
    private _formBuilder: FormBuilder,
    private _accountService: AccountService) { }

  ngOnInit() {
    //this.signUpForm = this._formBuilder.group({})
    this.firstFormGroup = this._formBuilder.group({
      //firstCtrl: ['', Validators.required]
      username:[''],
      email:[''],
      password:[''],
      //confirm_pass:['']
    });
    //this.secondFormGroup = this._formBuilder.group({
      //secondCtrl: ['', Validators.required]
    //});


    this._accountService.createAccount(this.account)
      .subscribe(data => this.account2 = data);

    console.log(this.account2)

  }

 /* onSubmit(){
    //this.accountService.register(this.firstFormGroup.value).pipe().subscribe(data => {this.router.navigate(['/home'])});
    this.http.post<any>("http://localhost:8085/api/v1/accounts",this.firstFormGroup.value)
    .subscribe(res=>{
      alert('SIGNIN SUCCESFUL');
      this.firstFormGroup.reset()
      this.router.navigate(["login"])
    },err=>{
      alert("Something went wrong")
    })
  }*/

  signUp(){
   const {username, email, password} = this.form;
    /*this.http.post<any>("http://localhost:8085/api/v1/accounts", this.firstFormGroup.value)
    .subscribe(res =>{
      alert("success")
      this.firstFormGroup.reset();
      this.router.navigate(['login']);
    },
    err=>{
      alert("error")

    })*/
    this._accountService.register(username, email, password).subscribe((data=> {
      console.log(data);
      this.signUpSuccessful=true;
    }))
  }



}

import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { HttpClient } from '@angular/common/http';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { ISecurityQuestion } from 'src/app/models/securityQuestions';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: {showError: true},
    },
  ],
})
export class SignUpComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  signUpSuccessful: boolean;

  account: Account;

  securityQuestions: ISecurityQuestion[]

  constructor( private _formBuilder: FormBuilder,
    private _accountService: AccountService,
    private router: Router) {

  }

  ngOnInit() {

    this.getSecQuestions()

    this.firstFormGroup = this._formBuilder.group({
      username : [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
      passwordRepeat: [null, Validators.required]
    });

    this.secondFormGroup = this._formBuilder.group({
      securityQuestion: [null, Validators.required],
      securityAnswer: [null, Validators.required]
    });

  }

  async getSecQuestions() {
    const res: any = await this._accountService.getSecurityQuestions().toPromise();
      this.securityQuestions = res;
      console.log(this.securityQuestions)
  }

  async onSignUp(){

    if (this.firstFormGroup.valid && this.secondFormGroup.valid) {
      this.account = {
        id: "",
        name: this.firstFormGroup.get('username')?.value,
        email: this.firstFormGroup.get('email')?.value,
        password: this.firstFormGroup.get('password')?.value,
        securityQuestion: this.secondFormGroup.get('securityQuestion')?.value,
        securityAnswer: this.secondFormGroup.get('securityAnswer')?.value
      }
      console.log(this.account)

      const res: any = await this._accountService.createAccount(this.account)
      .toPromise();
      this.account = res; //to get the actual id
      console.log(res)

      this.firstFormGroup.reset();  //check if you actually need it
      this.secondFormGroup.reset(); //same as above

      this.router.navigate(['home']);
    }
    else {
      //TODO: error message
    }
  }
}

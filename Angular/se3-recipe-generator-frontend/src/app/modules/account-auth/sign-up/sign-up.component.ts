import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { ISecurityQuestion } from 'src/app/models/securityQuestions';
import { throwError } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';


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

  visible1:boolean = true;
  changetype1:boolean =true;
  visible2:boolean = true;
  changetype2:boolean =true;

  isLinear:true;

  constructor( private _formBuilder: FormBuilder,
    private _accountService: AccountService,
    private _authService: AuthenticationService,
    private router: Router,
    private _snackBar:MatSnackBar,
    ) {

  }

  ngOnInit() {

    this.getSecQuestions()

    this.firstFormGroup = this._formBuilder.group({
      username : [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
      passwordRepeat: new FormControl("", Validators.required)
    }, {
      validator: this.ConfirmedValidator("password", "passwordRepeat") //works both ways
    });

    this.secondFormGroup = this._formBuilder.group({
      securityQuestion: [null, Validators.required],
      securityAnswer: [null, Validators.required]
    });

  }

  async getSecQuestions() {
    const res: any = await this._accountService.getSecurityQuestions();
      this.securityQuestions = res;
  }

  ConfirmedValidator(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (
        matchingControl.errors &&
        !matchingControl.errors['confirmedValidator']
      ) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ confirmedValidator: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
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

      const res: any = await this._accountService.createAccount(this.account);
      this.account = res; //to get the actual id

      this.firstFormGroup.reset();  //check if you actually need it
      this.secondFormGroup.reset(); //same as above

      this._accountService.sendOwnerAccountToServices(res.id)
      this._authService.login()

    }
    else if(this.firstFormGroup.invalid ||this.secondFormGroup.invalid){
      this._snackBar.open('Invalid input. Please look at the errors and try again.', 'Ok', {
        duration: 5000,
        panelClass: ['my-snackbar']
      });
      return;
    }
    else {
      (err: any) => {
        console.log(err)
        this.handleError(err)
      }
    }

  }

  viewpass(whichOne:number){
    if(whichOne==1){
      this.visible1 = !this.visible1;
      this.changetype1 = !this.changetype1;
    }
    else if(whichOne==2){
      this.visible2 = !this.visible2;
      this.changetype2 = !this.changetype2;
    }
  }

  handleError(err: { error: any; message: any; status: any; }) {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // if error is client-side error
      errorMessage = `Error: ${err.message}`;
    } else {
      // if error is server-side error
      errorMessage = `Error Code: ${err.status}\nMessage: ${err.message}`;
    }
    alert(errorMessage);
    return throwError(errorMessage);
  }
}

import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { ISecurityQuestion } from 'src/app/models/securityQuestions'
import { AccountService } from 'src/app/services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent implements OnInit {
  account: Account;
  securityQuestion: ISecurityQuestion[]; 

  constructor(private _accountService: AccountService, private router: Router){
    this.account = {
      id: "",
    name: "",
    email: "",
    password: "",
    securityQuestion: "",
    securityAnswer: ""
    }

  }

  async ngOnInit() {
    this.account = await this._accountService.getAccountInfo()
    this.securityQuestion = await this._accountService.getSecurityQuestions()
  }

  navigateToUpdateAccount(){ 
    this.router.navigate(['update_account'])
  }

  /* getSecurityQuestionText() { 
    forEach() { 

    }
  }
    
  
  if securityQuestion.enumValue == account.securityQuestion --> return securityQuestion.text */
}

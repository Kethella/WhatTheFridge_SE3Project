import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { ISecurityQuestion } from 'src/app/models/securityQuestions'
import { AccountService } from 'src/app/services/account.service';
import { Router, TitleStrategy } from '@angular/router';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent implements OnInit {
  account: Account;
  securityQuestion: ISecurityQuestion[];

  isDisabled: boolean = true;
  btnText: string = "Edit";
  fakePassword: string = "*********"

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

  changeDisabled() {
    if(this.isDisabled) {
      this.isDisabled = false;
      this.btnText = "Save"
      this.fakePassword = this.account.password;
    }
    else {
      this.isDisabled = true;
      this.btnText = "Edit"
      this.account.password = this.fakePassword;
      this.fakePassword = "*********";
      console.log(this.account)
      //this.updateAccount()
    }
  }

  async updateAccount() {
    let response: Account = await this._accountService.updateAccountInfo(this.account)
    console.log(response)
  }

  async deleteAccount() {
    //let response: Account = await this._accountService.deleteAccount()
    //console.log(response)
    this.router.navigate(['login']);
  }
}

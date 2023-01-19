import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent implements OnInit {
  account: Account;

  constructor(private _accountService: AccountService){
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
  }

}

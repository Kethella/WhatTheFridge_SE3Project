import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { Account } from 'src/app/models/account';
import { ISecurityQuestion } from 'src/app/models/securityQuestions'
import { AccountService } from 'src/app/services/account.service';
import { Router, TitleStrategy } from '@angular/router';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../fridge/fridge.component';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css'],
})
export class AccountViewComponent implements OnInit {
  account: Account;
  securityQuestions: ISecurityQuestion[];
  selectedSecurityQuestion: ISecurityQuestion;

  isDisabled: boolean = true;
  btnText: string = "Edit";
  fakePassword: string = "*********"
  fakeSecurityAnswer: string = "*********"
  messege: string;

  constructor(private _accountService: AccountService,
    private router: Router,
    private snackBar: MatSnackBar,
    public dialog: MatDialog){
    this.account = {
      id: "",
      name: "",
      email: "",
      password: "",
      securityQuestion: "",
      securityAnswer: ""
    }

    this.selectedSecurityQuestion = {enumValue: "", text: ""}

  }

  async ngOnInit() {
    this.account = await this._accountService.getAccountInfo()
    this.securityQuestions = await this._accountService.getSecurityQuestions()
    this.selectedSecurityQuestion = this.getSecurityQuestion(true, this.account.securityQuestion)
  }

  navigateToUpdateAccount(){
    this.router.navigate(['update_account'])
  }

  changeDisabled() {
    if(this.isDisabled) {
      this.isDisabled = false;
      this.btnText = "Save"
      this.fakePassword = this.account.password;
      this.fakeSecurityAnswer = this.account.securityAnswer;
    }
    else {
      if(this.everythingIsFine()) {
        this.isDisabled = true;
        this.btnText = "Edit"
        this.account.password = this.fakePassword;
        this.fakePassword = "*********";
        this.account.securityQuestion = this.getSecurityQuestion(false, this.selectedSecurityQuestion.text).enumValue;
        this.account.securityAnswer = this.fakeSecurityAnswer;
        this.fakeSecurityAnswer = "*********"
        console.log(this.account)
        //this.updateAccount()
      }
      else {
        console.log("bad bitch")
        let config = new MatSnackBarConfig();
        config.panelClass = ['my-snackbar']
        this.snackBar.open("Make sure there are no empty fields.", "Ok", config);

      }


    }
  }

  getSecurityQuestion(enumToText: boolean, input: string): ISecurityQuestion {
    if(enumToText) {
      for(let question of this.securityQuestions) {
        if(question.enumValue === input) {
          return question;
        }
      }
    }
    else {
      for(let question of this.securityQuestions) {
        if(question.text === input) {
          return question;
        }
      }
    }

    return {"enumValue": "", "text": ""};
  }

  everythingIsFine(): boolean {
    if(this.account.name === ""){

      return false;
    }
    if(this.account.email === ""){

      return false;
    }
    if(this.fakePassword === ""){

      return false;
    }
    if(this.fakeSecurityAnswer === ""){

      return false;
    }
    return true;
  }

  async updateAccount() {
    let response: Account = await this._accountService.updateAccountInfo(this.account)
    console.log(response)
  }

  async deleteAccount() {

    const dialogRef = this.dialog.open(DeleteAccountDialog, {
        width: '250px',
        data: {
          wantsToDelete: false
        },
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result.wantsToDelete) {
        this._accountService.deleteAccount()
        console.log("Deleted account")
        this.router.navigate(['login']);
      }

    })

  }
}


@Component({
  selector: 'dialog-delete-account-dialog',
  templateUrl: 'dialog-delete-account.html',
})
export class DeleteAccountDialog {

  wantsToDelete: boolean = false

  constructor(public dialogRef: MatDialogRef<DeleteAccountDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    }

  onYesClick(): void {
    this.data.wantsToDelete = true;
    this.dialogRef.close(this.data)
  }

  onNoClick(): void {
    this.data.wantsToDelete = false;
    this.dialogRef.close(this.data)
  }
}

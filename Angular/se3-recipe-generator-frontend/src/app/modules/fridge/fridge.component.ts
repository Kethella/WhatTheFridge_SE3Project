import { Component, OnInit, Inject} from '@angular/core';
import { FridgeService } from 'src/app/services/fridge.service';
import { FridgeItem } from 'src/app/models/fridgeItem';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-fridge',
  templateUrl: './fridge.component.html',
  styleUrls: ['./fridge.component.css']
})
export class FridgeComponent {
  public fridgeItems: FridgeItem[];
  text: string = "";
  makeNoItemsElementVisible = true;

  constructor(
    private _fridgeService: FridgeService,
    public dialog: MatDialog) {
      this.fridgeItems = []
  }

  ngOnInit() {
    this.loadItems();
  }

  async loadItems() {
    this.fridgeItems = await this._fridgeService.getFridgeItems();
    if(this.fridgeItems){
      this.makeNoItemsElementVisible = false;
      this.fridgeItems = this.fridgeItems.sort((a: FridgeItem, b: FridgeItem) => a.name.localeCompare(b.name));
    }
    else{
      this.makeNoItemsElementVisible = true;
    }
  }


  openAddDialog(): void {
    const dialogRef = this.dialog.open(NewFridgeItemDialog, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  async deleteItem(fridgeItem: FridgeItem) {
    this.text = await this._fridgeService.deleteItem(fridgeItem);
    this.ngOnInit();
  }

  handleDate(dateToFix: string): string {
    if (dateToFix.indexOf("T")) {
      var whereIsT = dateToFix.indexOf("T");
      var fixedDate = dateToFix.substring(0, whereIsT)
      return fixedDate;
    }
    else {
      return dateToFix;
    }
  }

  openEditDialog(fridgeItem: FridgeItem) {
    const dialogRef = this.dialog.open(EditFridgeItemDialog, {
      width: '500px',
      data: {
        selectedItem: fridgeItem
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }
}

@Component({
  selector: 'new-fridge-item-dialog',
  templateUrl: 'new-fridge-item-dialog.html',
  styleUrls: ['./new-fridge-item-dialog.css']
})
export class NewFridgeItemDialog implements OnInit{

  newItem = new FridgeItem;

  constructor(
    public dialogRef: MatDialogRef<NewFridgeItemDialog>,
    private _fridgeService: FridgeService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  async onAddClick() {
    if (this.emptyMandatoryFields()) {
      this.snackBar.open("Make sure that all mandatory fields are not empty.", "Ok", {
        duration: 5000,
        panelClass: ['my-snackbar']
      });
    }
    else {
      if(!isNaN(Number(this.newItem.amount))){
        this.newItem = await this._fridgeService.saveItem(this.newItem);
        this.dialogRef.close();
      } else{
        this.snackBar.open("Amount should be a number.", "Ok", {
          duration: 5000,
          panelClass: ['my-snackbar']
        });
      }
    }
  }

  emptyMandatoryFields(): boolean {
    if (this.newItem.name == null || this.newItem.name == "") {
      return true;
    }
    else if (this.newItem.amount == null) {
      return true;
    }
    else if (this.newItem.expirationDate == null || this.newItem.expirationDate == "") {
      return true;
    }
    else {
      return false;
    }
  }
}

@Component({
  selector: 'edit-fridge-item-dialog',
  templateUrl: './edit-fridge-item-dialog.html',
  styleUrls: ['./edit-fridge-item-dialog.css']
})
export class EditFridgeItemDialog implements OnInit{

  selectedItem = new FridgeItem;

  constructor(public dialogRef: MatDialogRef<EditFridgeItemDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private _fridgeService: FridgeService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.selectedItem = this.data.selectedItem;
  }

  onCancelClick(): void {
    this.dialogRef.close();

  }

  async onSaveClick() {
    if (this.emptyMandatoryFields()) {
      this.snackBar.open("Make sure that all mandatory fields are not empty.", "Ok", {
        duration: 5000,
        panelClass: ['my-snackbar']
      });
    }
    else {
      if(!isNaN(Number(this.selectedItem.amount))){
        this.selectedItem = await this._fridgeService.updateItem(this.selectedItem);
        this.dialogRef.close();
      } else{
        this.snackBar.open("Amount should be a number.", "Ok", {
          duration: 5000,
          panelClass: ['my-snackbar']
        });
      }
    }
  }

  emptyMandatoryFields(): boolean {
    if (this.selectedItem.name == null || this.selectedItem.name == "") {
      return true;
    }
    else if (this.selectedItem.amount == null) {
      return true;
    }
    else if (this.selectedItem.expirationDate == null || this.selectedItem.expirationDate == "") {
      return true;
    }
    else {
      return false;
    }
  }
}

export interface DialogData {
  selectedItem: FridgeItem
}


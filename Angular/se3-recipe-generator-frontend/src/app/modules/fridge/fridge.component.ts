import { Component, OnInit, Inject} from '@angular/core';
import { FridgeService } from 'src/app/services/fridge.service';
import { FridgeItem } from 'src/app/models/fridgeItem';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

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
    console.log("delete and restart");
    this.text = await this._fridgeService.deleteItem(fridgeItem);
    console.log(this.text)
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
    private _fridgeService: FridgeService) {
  }

  ngOnInit() {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  async onAddClick() {
    this.newItem = await this._fridgeService.saveItem(this.newItem);
    console.log(this.newItem)
    this.dialogRef.close();
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
    private _fridgeService: FridgeService) {
  }

  ngOnInit() {
    this.selectedItem = this.data.selectedItem;
    console.log(this.selectedItem)
    console.log(this.selectedItem.name)
  }

  onCancelClick(): void {
    this.dialogRef.close();

  }

  async onSaveClick() {
    this.selectedItem = await this._fridgeService.updateItem(this.selectedItem);
    console.log("updated:")
    console.log(this.selectedItem)
    this.dialogRef.close();
  }
}

export interface DialogData {
  selectedItem: FridgeItem
}


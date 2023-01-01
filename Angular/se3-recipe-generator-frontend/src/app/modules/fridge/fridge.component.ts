import { Component, OnInit} from '@angular/core';
import { FridgeService } from 'src/app/services/fridge.service';
import { FridgeItem } from 'src/app/models/fridgeItem';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-fridge',
  templateUrl: './fridge.component.html',
  styleUrls: ['./fridge.component.css']
})
export class FridgeComponent {
  public fridgeItems = [] as any;

  constructor(
    private _fridgeService: FridgeService,
    public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.fridgeItems = await this._fridgeService.getFridgeItems();
    this.fridgeItems = this.fridgeItems.sort((a: FridgeItem, b: FridgeItem) => a.name.localeCompare(b.name));
  }

  async restart() {
    this.fridgeItems = await this._fridgeService.getFridgeItems();
    this.fridgeItems = this.fridgeItems.sort((a: FridgeItem, b: FridgeItem) => a.name.localeCompare(b.name));
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NewFridgeItemDialog, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.restart()
    });
  }
}

@Component({
  selector: 'new-fridge-item-dialog',
  templateUrl: 'new-fridge-item-dialog.html',
  styleUrls: ['./new-fridge-item-dialog.css']
})
export class NewFridgeItemDialog implements OnInit{

  newItem: FridgeItem;

  constructor(
    public dialogRef: MatDialogRef<NewFridgeItemDialog>,
    private _fridgeService: FridgeService) {
  }

  async ngOnInit() {

  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  onAddClick(): void {
    this._fridgeService.saveItem(this.newItem);
    this.dialogRef.close();
  }
}


import { Component, OnInit } from '@angular/core';
import { RecipeDetailsComponent } from './components/recipe-details/recipe-details.component';
import { MatDialog } from '@angular/material/dialog';


import { AccountService } from './services/account.service';
import { withLatestFrom } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  title = 'se3-recipe-generator-frontend';

  constructor(public dialog:MatDialog){}
  ngOnInit(): void{}

  openDialog(){
    this.dialog.open(RecipeDetailsComponent, {
      width: '650px',
      height: '450px'
    }); 
  }
}

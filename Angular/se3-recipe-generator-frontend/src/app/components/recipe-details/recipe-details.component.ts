import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {
  recipes: Recipe[]; 

  constructor(
    public dialogRef: MatDialogRef<RecipeDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private recipeService: RecipeService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
  
  public icon = 'favorite_border'
  markFavourite(newIcon: string){
    this.icon = newIcon; 
  }

  ngOnInit() {
    this.recipeService.findAll().subscribe(data => {
      this.recipes = data;
    });

}
}

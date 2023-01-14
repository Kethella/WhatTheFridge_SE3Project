import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { MatDialog } from '@angular/material/dialog';
import { RecipeDetailsComponent} from 'src/app/shared/components/recipe-details/recipe-details.component';



@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent {

  @Input() public recipes;

  @Output() public restartAfterDeleteEvent = new EventEmitter();


  constructor(private _recipeService: RecipeService,
    public dialog:MatDialog) {
  }

  ngOnInit(){
    console.log("Hi")
  }

  delete(recipe: Recipe){
    //this._recipeService.delete(recipe) create the method in the service
    this.restartAfterDeleteEvent.emit()
    console.log("delete")
  }

  openDialog(selectedRecipe: Recipe){
    this.dialog.open(RecipeDetailsComponent, {
      width: '900px',
      data: {
        selectedRecipe: selectedRecipe
      }
    });
  }
}

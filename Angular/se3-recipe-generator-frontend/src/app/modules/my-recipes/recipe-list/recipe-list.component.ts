import { HttpClient, HttpParams } from '@angular/common/http';
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
  
  constructor(private recipeService:RecipeService,
    public dialog:MatDialog) {
  }

  ngOnInit(){
    console.log("Hi")
  }

  delete(id: string){
    this.restartAfterDeleteEvent.emit()
    this.recipeService.deleteRecipe(id);
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

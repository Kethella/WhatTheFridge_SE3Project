import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { MatDialog } from '@angular/material/dialog';
import { RecipeDetailsComponent} from 'src/app/shared/components/recipe-details/recipe-details.component';
import { EditRecipeComponent } from './edit-recipe/edit-recipe.component';


@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent {

  @Input() public recipes;

  @Output() public restartAfterEvent = new EventEmitter();

  constructor(private _recipeService:RecipeService,
    public dialog:MatDialog) {
  }

  ngOnInit(){
  }

  async deleteRecipe(recipe: Recipe){
    const text = await this._recipeService.deleteRecipe(recipe);
    this.restartAfterEvent.emit()
  }

  openDetailsDialog(selectedRecipe: Recipe){

    const dialogRef = this.dialog.open(RecipeDetailsComponent, {
      width: '900px',
      data: {
        selectedRecipe: selectedRecipe
      }
    });

  }

  openDialogEdit(selectedRecipe: Recipe){

    const dialogRef = this.dialog.open(EditRecipeComponent, {
      width: '900px',
      data: {
        selectedRecipe: selectedRecipe
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.restartAfterEvent.emit()
    });
  }
}

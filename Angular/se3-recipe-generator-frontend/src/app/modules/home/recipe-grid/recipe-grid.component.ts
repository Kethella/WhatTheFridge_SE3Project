import { Component, OnInit, Input } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';
import { HttpParams} from '@angular/common/http';
import { RecipeDetailsComponent } from 'src/app/shared/components/recipe-details/recipe-details.component';
import { MatDialog } from '@angular/material/dialog';
import { Recipe } from 'src/app/models/recipe';

@Component({
  selector: 'app-recipe-grid',
  templateUrl: './recipe-grid.component.html',
  styleUrls: ['./recipe-grid.component.css']
})
export class RecipeGridComponent implements OnInit{

  @Input() public recipes = [] as any;
  public breakpoint: number = 6;

  constructor(public dialog:MatDialog){}

  public queryParams = new HttpParams();

  ngOnInit(): void {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 4;
  }

  onResize(event: any) {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 4;
  }

  openDialog(selectedRecipe: Recipe){
    this.dialog.open(RecipeDetailsComponent, {
      width: '900px',
      //height: '450px',
      data: {
        selectedRecipe: selectedRecipe
      }
    });
  }

}



import { HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';
@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent {

  @Input() public recipes = [] as any;

  public queryParams = new HttpParams();

  constructor(private _recipeService: RecipeService) {
  }
  async ngOnInit(){
    this.recipes=await this._recipeService.getRecipes(this.queryParams);
    this.queryParams=this.queryParams.append("defaultRecipes", "no");
    console.log("sth happened")
  }
  delete(){
    console.log("delete")
  }
}

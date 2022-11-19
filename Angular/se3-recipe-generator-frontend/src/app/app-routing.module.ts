import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyPagesComponent } from './components/dummy-pages/dummy-pages.component';

//TODO: Replace with actual paths, change routerLinks in navbar component
const routes: Routes = [
  {path: 'home', component: DummyPagesComponent},
  {path: 'fridge', component: DummyPagesComponent},
  {path: 'my_recipes', component: DummyPagesComponent},
  {path: 'fav_recipes', component: DummyPagesComponent},
  {path: 'account_settings', component: DummyPagesComponent},
  {path: 'sign_in', component: DummyPagesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

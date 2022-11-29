import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/account/login/login.component';
import { SignUpComponent } from './components/account/sign-up/sign-up.component';
import { DummyPagesComponent } from './components/dummy-pages/dummy-pages.component';

//TODO: Replace with actual paths, change routerLinks in navbar component
const routes: Routes = [
  {path: 'home', component: DummyPagesComponent},
  {path: 'fridge', component: DummyPagesComponent},
  {path: 'my_recipes', component: DummyPagesComponent},
  {path: 'fav_recipes', component: DummyPagesComponent},
  {path: 'account_settings', component: DummyPagesComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'login', component:LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DummyPagesComponent } from './modules/dummy-pages/dummy-pages.component';
import { LoggedInLayoutComponent } from './layouts/logged-in-layout/logged-in-layout.component';
import { HomeComponent } from './modules/home/home.component';
import { LoginComponent } from './modules/account-auth/login/login.component';
import { SignUpComponent } from './modules/account-auth/sign-up/sign-up.component';

//TODO: Replace with actual paths, change routerLinks in navbar component
const routes: Routes = [
  {
    path: '',
    component: LoggedInLayoutComponent,
    children: [
      {path: '', component: HomeComponent},
      {path: 'home', component: HomeComponent},
      {path: 'fridge', component: DummyPagesComponent},
      {path: 'my_recipes', component: DummyPagesComponent},
      {path: 'fav_recipes', component: DummyPagesComponent},
      {path: 'account_settings', component: DummyPagesComponent},
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignUpComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

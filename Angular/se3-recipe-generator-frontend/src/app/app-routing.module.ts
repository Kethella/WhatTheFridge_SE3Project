import { Injectable, NgModule } from '@angular/core';
import { RouterModule, RouterStateSnapshot, Routes, TitleStrategy } from '@angular/router';

import { DummyPagesComponent } from './modules/dummy-pages/dummy-pages.component';
import { LoggedInLayoutComponent } from './layouts/logged-in-layout/logged-in-layout.component';
import { HomeComponent } from './modules/home/home.component';
import { LoginComponent } from './modules/account-auth/login/login.component';
import { SignUpComponent } from './modules/account-auth/sign-up/sign-up.component';
import { Title } from '@angular/platform-browser';
// import { FridgeComponent } from './modules/fridge/fridge.component';
import { AccountViewComponent } from './modules/account-view/account-view.component';
import { UpdateProfileComponent } from './modules/update-profile/update-profile.component';

//TODO: Replace with actual paths, change routerLinks in navbar component
const routes: Routes = [
  {
    path: '',
    title: 'Login',
    component: LoginComponent},
  {
    path: '',
    title: '',
    component: LoggedInLayoutComponent,
    children: [
      {
        path: 'home',
        title: 'Home',
        component: HomeComponent},
      //{
      //  path: 'fridge',
      //  title: 'Fridge',
      //  component: FridgeComponent},
      {
        path: 'my_recipes',
        title: 'My Recipes',
        component: DummyPagesComponent
      },
      {
        path: 'account_settings',
        title: 'Account',
        component: AccountViewComponent},
        {
          path: 'update_account',
          title: 'Edit Profile',
          component: UpdateProfileComponent},
    ]
  },
  {
    path: 'login',
    title: 'Login',
    component: LoginComponent},
  {
    path: 'sign-up',
    title: 'Sign up',
    component: SignUpComponent}
];

@Injectable({providedIn: 'root'})
export class TemplatePageTitleStrategy extends TitleStrategy {
  constructor(private readonly title: Title) {
    super();
  }

  override updateTitle(routerState: RouterStateSnapshot) {
    const title = this.buildTitle(routerState);
    if (title !== undefined) {
      this.title.setTitle(`${title} | What The Fridge`);
    }
  }
}

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    {
      provide: TitleStrategy,
      useClass: TemplatePageTitleStrategy
    }
  ]
})
export class AppRoutingModule { }


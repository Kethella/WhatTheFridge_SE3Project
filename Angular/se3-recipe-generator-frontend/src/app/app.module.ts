import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AccountComponent } from './components/account/account.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import {MatMenuModule} from '@angular/material/menu';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { DummyPagesComponent } from './components/dummy-pages/dummy-pages.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { SignUpComponent } from './components/account/sign-up/sign-up.component';
import { LoginComponent } from './components/account/login/login.component';
import {MatStepperModule} from '@angular/material/stepper';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { RecipeDetailsComponent } from './components/recipe-details/recipe-details.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { RecipeService } from './services/recipe.service';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    NavbarComponent,
    DummyPagesComponent,
    SignUpComponent,
    LoginComponent,
    RecipeDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule, 
    MatButtonModule,
    MatIconModule, 
    MatMenuModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatStepperModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDialogModule,
    DragDropModule
  ],
  providers: [RecipeService],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { Component } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent {
  userName = 'old User Name'
  password = 'old Password'
  value= 'Old Profile Info'
  eMail= 'old E-Mail'
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);

  constructor(private router: Router){

  }

  navigateToAccountView(){ 
    this.router.navigate(['account_settings'])
  }
}

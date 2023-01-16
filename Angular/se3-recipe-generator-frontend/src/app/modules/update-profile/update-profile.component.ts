import { Component } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent {
  userName = 'old User Name'
  password = 'old Password'
  value= 'Old Profile Info'
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
}

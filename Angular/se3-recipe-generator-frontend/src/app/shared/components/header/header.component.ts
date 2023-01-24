import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { interval } from 'rxjs';
import { FridgeItem } from 'src/app/models/fridgeItem';
import { FridgeService, NotifItem } from 'src/app/services/fridge.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public expFrigdeItems: NotifItem[];
  public count: number;

  constructor(private _fridgeService: FridgeService,
    private route: ActivatedRoute, private router: Router) {

      interval(5000).subscribe(x => {
        this.getNotif();
      })
    }

  async ngOnInit() {
    this.getNotif()
  }

  async getNotif() {
    this.expFrigdeItems = await this._fridgeService.getUpdatedNotifications();
    this.count = this.expFrigdeItems.length;

  }
}

import { Component, OnInit, Input } from '@angular/core';

import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profile-info',
  inputs: ['user'],
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.css']
})
export class ProfileInfoComponent implements OnInit {

  @Input('user') user: User;

  constructor() { }

  ngOnInit(): void {
  }

}

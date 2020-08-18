import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {

  user: {};

  constructor( ) { }

  ngOnInit(): void {  }
}

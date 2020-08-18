import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-authentication-card',
  templateUrl: './authentication-card.component.html',
  styleUrls: ['./authentication-card.component.css']
})
export class AuthenticationCardComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  tabs = [
    'signup-tab',
    'login-tab'
  ];

  onSelected(s: string): void {
    this.tabs.forEach(tab => {
      var element = document.getElementById(tab)

      var isSelected = tab === s

      if (isSelected) {
        element.classList.add('active')
      } else {
        element.classList.remove('active')
      }
      element.setAttribute("aria-selected", String(isSelected))
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-authentication-card',
  templateUrl: './authentication-card.component.html',
  styleUrls: ['./authentication-card.component.css']
})
export class AuthenticationCardComponent implements OnInit {

  authenticated: boolean = false;

  tabs = [
    'signup-tab',
    'login-tab'
  ];

  constructor(private token: TokenStorageService) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    this.authenticated = this.token.getUser() != null;
  }

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

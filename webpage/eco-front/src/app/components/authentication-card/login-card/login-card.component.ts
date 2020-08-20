import { Component, OnInit } from '@angular/core';

import { AuthService }         from '../../../services/auth.service'
import { TokenStorageService } from '../../../services/token-storage.service'

@Component({
  selector: 'login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {

  form: any = { };
  isLoggedIn    = false;
  isLoginFailed = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {

    /*
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    */
  }

  onSubmit():void {
    this.authService.login(this.form).subscribe(
      data => {
        // this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        console.log(data);

        this.isLoginFailed = false;
        this.isLoggedIn    = true;
        this.reloadPage();
      },
      err => {
        this.errorMessage  = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

}

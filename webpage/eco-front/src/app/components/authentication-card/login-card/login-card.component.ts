import { Component, OnInit } from '@angular/core';

import { AuthService }         from '../../../services/auth.service'
import { TokenStorageService } from '../../../services/token-storage.service'
import { Router } from '@angular/router';

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
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit(): void {

    /*
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    */
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      data => {
        // this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn    = true;

        this.goToHome()
      },
      err => {
        this.errorMessage  = err.message;
        this.isLoginFailed = true;
      }
    );
  }

  onSubmitGoogle(): void {
    this.router.navigate(['404']);
  }

  goToHome(): void {
    this.router.navigate(['profile']);
  }

}

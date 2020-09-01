import { Component, OnInit } from '@angular/core';

import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'signup-card',
  templateUrl: './signup-card.component.html',
  styleUrls: ['./signup-card.component.css']
})
export class SignupCardComponent implements OnInit {

  form: any = { };
  isSignedUp     = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.form.password === this.form.c_password) {
      var data = new Object({
        username:       this.form.username,
        name:           this.form.name,
        surname:        this.form.surname,
        phone:          this.form.phone,
        email:          this.form.email,
        password:       this.form.password
      });

      this.authService.register(data).subscribe(
        data => {
          this.tokenStorage.saveUser(data);

          this.isSignedUp     = true;
          this.isSignUpFailed = false;

          console.log(data);
        },
        err => {
          this.errorMessage   = err.message;
          this.isSignUpFailed = true;
        }
      );

    } else {
      this.errorMessage   = "The passwords do not match!";
      this.isSignUpFailed = true;
    }

  }

  onSubmitGoogle(): void {
    this.router.navigate(['404']);
  }

}

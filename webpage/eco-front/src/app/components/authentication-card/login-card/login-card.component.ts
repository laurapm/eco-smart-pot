import { Component, OnInit } from '@angular/core';
//import { Router }            from '@angular/router'
//import { HttpClient }        from '@angular/common/http'

@Component({
  selector: 'login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {

  user: {};

  constructor(
//    private http:   HttpClient,
//    private router: Router
  ) { }

  ngOnInit(): void {  }

  /*
  registerUser() {
    this.http.post('/owner', this.user)
      .subscribe(res => {
          this.router.navigate(['/user-register', res]);
      }, (err) => {
        console.log(err);
      }
    );
  }
  */
}

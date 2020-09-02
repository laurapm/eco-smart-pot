import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://ch1r0n.duckdns.org:8888/api/';

const httpOptions = {
  headers: new HttpHeaders()
    .set('content-type', 'application/json')
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'login/', {
      user:     credentials.user,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    console.log('the service works too')

    return this.http.post(AUTH_API + 'signup/', {
      username:       user.username,
      name:           user.name,
      surname:        user.surname,
      phone:          user.phone,
      email:          user.email,
      password:       user.password
    }, httpOptions);
  }

}

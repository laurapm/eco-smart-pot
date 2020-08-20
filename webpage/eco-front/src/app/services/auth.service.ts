import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/';

const httpOptions = {
  headers: new HttpHeaders()
    .set('content-type', 'application/json')
    //.set('Access-Control-Allow-Origin', '*')
    //.set('Access-Control-Allow-Headers', 'X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method')
    //.set('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE')
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'login/', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(AUTH_API + 'register/', {
      username:       user.username,
      name:           user.name,
      surname:        user.surname,
      courtesy_title: user.title,
      phone:          user.phone,
      email:          user.email,
      password:       user.password
    }, httpOptions);
  }

}

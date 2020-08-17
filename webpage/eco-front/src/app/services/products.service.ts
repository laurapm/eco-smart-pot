import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const baseURL = 'http://localhost:9200/';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(baseURL);
  }

  get(id): Observable<any> {
    return this.http.get(`${baseURL}/${id}`);
  }

  findByName(name): Observable<any> {
    return this.http.get(`${baseURL}?name=${name}`);
  }

  update (id, data): Observable<any> {
    return this.http.put(`${baseURL}/${id}`, data);
  }
}

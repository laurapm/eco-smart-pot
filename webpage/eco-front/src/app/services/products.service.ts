import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Product } from '../models/product';
import { TicketRequest } from '../models/ticketRequest';
import { HandleErrorService } from './handle-error.service';

const API = 'http://localhost:8080/api';

const httpOptions = {
  headers: new HttpHeaders()
    .set('content-type', 'application/json')
};

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(
    private http: HttpClient,
    private handle: HandleErrorService) { }

  getProducts(): Observable<any> {
    return this.http.get<Product[]>(
      API + "/products"
    ).pipe(
      catchError(this.handle.handleError<Product[]>('getDevices', []))
    );
  }

  getProductsBetweenPrices(from: number, to: number): Observable<any> {
    return this.http.post<Product[]>(
      API + "/prices", {
        minPrice: from,
        maxPrice: to
      }
    ).pipe(
      catchError(this.handle.handleError<Product[]>('getProductsBetweenPrices', []))
    );
  }

  buyProducts(purchase: TicketRequest): Observable<any> {
    return this.http.post(
      API + "/tickets", purchase
    ).pipe(
      catchError(this.handle.handleError('buyProducts', []))
    );
  }

}

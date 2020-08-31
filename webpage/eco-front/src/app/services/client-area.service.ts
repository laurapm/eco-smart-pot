import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, ObservedValueOf } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Device }             from '../models/device';
import { Plant }              from '../models/plant';
import { Reminder }           from '../models/reminder';
import { Treatment }          from '../models/treatment';
import { Measurements }       from '../models/measurements';
import { HandleErrorService } from '../services/handle-error.service';

const API = 'http://localhost:8080/api'

const httpOptions = {
  headers: new HttpHeaders()
    .set('content-type', 'application/json')
};

@Injectable({
  providedIn: 'root'
})
export class ClientAreaService {

  constructor(
    private http:  HttpClient,
    private handle: HandleErrorService) { }

  getDevices(user_id: any): Observable<any> {
    return this.http.get<Device[]>(
      API + '/devices/owned/' + user_id).pipe(
        catchError(this.handle.handleError<Device[]>('getDevices', []))
      );
  }

  getPlant(plant_id: any): Observable<any> {
    return this.http.get<Plant>(
      API + '/plants/' + plant_id).pipe(
        catchError(this.handle.handleError<Device>('getPlants', null))
      );
  }

  getReminders(device_id: string): Observable<any> {
    return this.http.post<Reminder[]>(
      API + '/reminders/programmed', {
        device: device_id,
        today:  new Date()
    }).pipe(
      catchError(this.handle.handleError<Reminder[]>('getReminders', []))
    );
  }

  getTreatments(device_id: string): Observable<any> {
    return this.http.post<Treatment[]>(
      API + '/treatments/programmed', {
        device: device_id,
        today: new Date()
      })
      .pipe(
        catchError(this.handle.handleError<Treatment[]>('getTreatments', []))
      );
  }

  getMeasurements(device_id: string, yesterday: Date) {
    return this.http.post<Measurements[]>(
      API + "/measurements/after", {
        device: device_id,
        today: yesterday
      })
      .pipe(
        catchError(this.handle.handleError<Measurements[]>('getMeasurements', []))
      );
  }
}

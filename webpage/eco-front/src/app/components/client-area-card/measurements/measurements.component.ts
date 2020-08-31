import { Component, OnInit, Input } from '@angular/core';

import { Device } from 'src/app/models/device';
import { ClientAreaService } from 'src/app/services/client-area.service';
import { Measurements } from 'src/app/models/measurements';

@Component({
  selector: 'app-measurements',
  templateUrl: './measurements.component.html',
  styleUrls: ['./measurements.component.css']
})
export class MeasurementsComponent implements OnInit {

  @Input('measurements') measurements: any = {};

  colorHumInt: Array<any> = [
    {
      backgroundColor: 'rgba(54, 162, 235, .2)',
      borderColor:     'rgba(54, 162, 235, .7)',
      borderWidth: 2,
    }];
    colorHumExt: Array<any> = [
    {
      backgroundColor: 'rgba(75, 192, 192, .2)',
      borderColor:     'rgba(75, 192, 192, .7)',
      borderWidth: 2,
    }];
    colorLum: Array<any> = [
    {
      backgroundColor: 'rgba(255, 206, 86, .2)',
      borderColor:     'rgba(255, 206, 86, .7)',
      borderWidth: 2,
    }];
    colorTemp: Array<any> = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor:     'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    }];

  errorMessage = '';

  constructor(private api: ClientAreaService) { }

  ngOnInit(): void {
  }

}

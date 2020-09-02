import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from 'src/app/services/token-storage.service';
import { ClientAreaService }   from 'src/app/services/client-area.service';
import { Device }    from 'src/app/models/device';
import { Plant }     from 'src/app/models/plant';
import { Reminder }  from 'src/app/models/reminder';
import { Treatment } from 'src/app/models/treatment';
import { User }      from 'src/app/models/user';
import { Measurements } from 'src/app/models/measurements';

@Component({
  selector: 'app-client-area-card',
  templateUrl: './client-area-card.component.html',
  styleUrls: ['./client-area-card.component.css']
})
export class ClientAreaCardComponent implements OnInit {

  devices: Device[] = [ ];

  plants:       Map<string, Plant>     = new Map;
  reminders:    Map<string, Reminder>  = new Map;
  treatments:   Map<string, Treatment> = new Map;
  measurements: Map<string, any>       = new Map;

  isAuthenticated: boolean = false;
  userProfile: User = null;
  selectedDevice: Device;
  selectedMeasure: any;

  errorMessage = '';

  constructor(
    private token: TokenStorageService,
    private api: ClientAreaService
  ) { }

  ngOnInit(): void {
    this.getUser();
    if (this.userProfile != null) {
      this.getDevices();
    }
  }

  getUser(): void {
    this.userProfile = this.token.getUser();
    this.isAuthenticated = this.userProfile != null;
  }

  getDevices(): void {
    const user_id = this.token.getUser().id;

    this.api.getDevices(user_id).subscribe(
      data => {
        data.forEach((element: Device) => {
          element.registryDate = new Date(element.registryDate);

          this.devices.push   (element);
          this.getPlants      (element.plant);
          this.getReminders   (element.id);
          this.getTreatments  (element.id);
          this.getMeasurements(element.id);
        });
      },
      err => {
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    )
  }

  getPlants(plant_id: string): void {
    let alreadyStored = this.plants.has(plant_id);

    if (!alreadyStored) {
      this.api.getPlant(plant_id).subscribe(
        data => {
          this.plants.set(data.id, data);
        },
        err => {
          this.errorMessage  = err.message;
          console.error(this.errorMessage);
        }
      );
    }
  }

  getReminders(device_id: string): void {
    this.api.getReminders(device_id).subscribe(
      data => {
        data.forEach( (element: Reminder) => {
          element.requestTime   = new Date(element.requestTime);
          element.remindingTime = new Date(element.remindingTime);

          this.reminders.set(device_id, element);
        });
      },
      err => {
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    );
  }

  getTreatments(device_id: string): void {
    this.api.getTreatments(device_id).subscribe(
      data => {
        data.forEach( (element: Treatment) => {
          element.requestTime = new Date(element.requestTime);
          element.actionTime  = new Date(element.actionTime);

          this.treatments.set(device_id, element);
        });

      },
      err => {
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    );
  }

  getMeasurements(device_id: string) {
    let yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);

    this.api.getMeasurements(device_id, yesterday).subscribe(
      data => {
        let info = {
          humidityInt: {
            data: [],
            label: 'Internal Humidity',
            labels: []
          },
          humidityExt: {
            data: [],
            label: 'External Humidity',
            labels: []
          },
          luminosity:  {
            data: [],
            label: 'External Luminosity',
            labels: []
          },
          temperature: {
            data: [],
            label: 'External Temperature',
            labels: []
          },
        };

        data.forEach( (element: Measurements) => {
          // Internal Humidity
          element.humidityInt.forEach( measure => {
            info.humidityInt.data.push(measure.measure);
            info.humidityInt.labels.push(element.hour + ":" + measure.minute);
          });
          // External Humidity
          element.humidityExt.forEach( measure => {
            info.humidityExt.data.push(measure.measure);
            info.humidityExt.labels.push(element.hour + ":" + measure.minute);
          });
          // External Luminosity
          element.luminosityExt.forEach( measure => {
            info.luminosity.data.push(measure.measure);
            info.luminosity.labels.push(element.hour + ":" + measure.minute);
          });
          // External Temperature
          element.temperatureExt.forEach( measure => {
            info.temperature.data.push(measure.measure);
            info.temperature.labels.push(element.hour + ":" + measure.minute);
          });
        });

        this.measurements.set(device_id, info);
      },
      err => {
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    );
  }

  onEmission(device: Device) {
    this.selectedDevice = device;
    this.selectedMeasure = this.measurements.get(device.id);
  }

}

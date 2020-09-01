import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { Device }              from '../../../models/device';
import { Plant }               from '../../../models/plant';

@Component({
  selector: 'app-devices-list',
  inputs: ['devices', 'plants'],
  templateUrl: './devices-list.component.html',
  styleUrls: ['./devices-list.component.css']
})
export class DevicesListComponent implements OnInit {

  @Input('devices') devices: Device[] = [ ];
  @Input('plants')  plants  = new Map();
  @Output() deviceEmitter = new EventEmitter<Device>();
  selectedDevice: Device;
  selectedPlant:  Plant;

  constructor() { }

  ngOnInit(): void {
  }

  onSelect(device: Device): void {
    this.selectedDevice = device;
    this.deviceEmitter.emit(device);
    this.selectedPlant = this.plants.get(this.selectedDevice.plant);
  }

}

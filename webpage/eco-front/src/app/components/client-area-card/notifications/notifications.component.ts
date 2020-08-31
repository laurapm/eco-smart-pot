import { Component, OnInit, Input } from '@angular/core';

import { ClientAreaService } from '../../../services/client-area.service';
import { Reminder }          from 'src/app/models/reminder';
import { Treatment }         from 'src/app/models/treatment';
import { Device }            from 'src/app/models/device';

@Component({
  selector: 'app-notifications',
  inputs: ['reminders', 'treatments'],
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  @Input('reminders')  reminders: Map<string, Reminder>   = new Map();
  @Input('treatments') treatments: Map<string, Treatment> = new Map();
  @Input('selectedDevice') selected: Device;

  constructor(private api: ClientAreaService) { }

  ngOnInit(): void {
  }

}

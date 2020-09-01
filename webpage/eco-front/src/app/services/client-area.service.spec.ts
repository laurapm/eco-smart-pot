import { TestBed } from '@angular/core/testing';

import { ClientAreaService } from './client-area.service';

describe('ClientAreaService', () => {
  let service: ClientAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientAreaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

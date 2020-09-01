import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientAreaCardComponent } from './client-area-card.component';

describe('ClientAreaCardComponent', () => {
  let component: ClientAreaCardComponent;
  let fixture: ComponentFixture<ClientAreaCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClientAreaCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientAreaCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Page202AcceptedComponent } from './page202-accepted.component';

describe('Page202AcceptedComponent', () => {
  let component: Page202AcceptedComponent;
  let fixture: ComponentFixture<Page202AcceptedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Page202AcceptedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Page202AcceptedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

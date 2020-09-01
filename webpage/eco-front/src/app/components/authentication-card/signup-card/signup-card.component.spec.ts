import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupCardComponent } from './signup-card.component';

describe('SignupCardComponent', () => {
  let component: SignupCardComponent;
  let fixture: ComponentFixture<SignupCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignupCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

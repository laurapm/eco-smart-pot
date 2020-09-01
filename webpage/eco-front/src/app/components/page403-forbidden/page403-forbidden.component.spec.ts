import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Page403ForbiddenComponent } from './page403-forbidden.component';

describe('Page403ForbiddenComponent', () => {
  let component: Page403ForbiddenComponent;
  let fixture: ComponentFixture<Page403ForbiddenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Page403ForbiddenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Page403ForbiddenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

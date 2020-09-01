import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Page401UnauthorizedComponent } from './page401-unauthorized.component';

describe('Page401UnauthorizedComponent', () => {
  let component: Page401UnauthorizedComponent;
  let fixture: ComponentFixture<Page401UnauthorizedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Page401UnauthorizedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Page401UnauthorizedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

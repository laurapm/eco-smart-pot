import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeasurementsChartComponent } from './measurements-chart.component';

describe('MeasurementsChartComponent', () => {
  let component: MeasurementsChartComponent;
  let fixture: ComponentFixture<MeasurementsChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeasurementsChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeasurementsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

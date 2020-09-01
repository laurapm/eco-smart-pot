import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AFunnyChartComponent } from './a-funny-chart.component';

describe('AFunnyChartComponent', () => {
  let component: AFunnyChartComponent;
  let fixture: ComponentFixture<AFunnyChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AFunnyChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AFunnyChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

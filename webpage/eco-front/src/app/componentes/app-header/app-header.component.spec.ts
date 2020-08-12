import { LayoutModule } from '@angular/cdk/layout';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';

import { AppHeaderComponent } from './app-header.component';

describe('AppHeaderComponent', () => {
  let component: AppHeaderComponent;
  let fixture: ComponentFixture<AppHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppHeaderComponent],
      imports: [
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatSidenavModule,
        MatToolbarModule,
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});

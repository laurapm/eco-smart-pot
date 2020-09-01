import { BrowserModule }           from '@angular/platform-browser';
import { HttpClientModule }        from '@angular/common/http';
import { NgModule }                from '@angular/core';
import { LayoutModule }            from '@angular/cdk/layout';
import { FormsModule }             from '@angular/forms';
import { MatToolbarModule }        from '@angular/material/toolbar';
import { MatButtonModule }         from '@angular/material/button';
import { MatSidenavModule }        from '@angular/material/sidenav';
import { MatIconModule }           from '@angular/material/icon';
import { MatListModule }           from '@angular/material/list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ng2TelInputModule }       from 'ng2-tel-input';

import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent }     from './app.component';

// Header
import { AppHeaderComponent } from './components/app-header/app-header.component';
// Home
import { HomeCardComponent } from './components/home-card/home-card.component';
// Authentication
import { AuthenticationCardComponent } from './components/authentication-card/authentication-card.component';
import { LoginCardComponent }          from './components/authentication-card/login-card/login-card.component';
import { SignupCardComponent }         from './components/authentication-card/signup-card/signup-card.component';
// Shopping Cart
import { ShoppingCardComponent } from './components/shopping-card/shopping-card.component';
import { FiltersComponent }      from './components/shopping-card/filters/filters.component';
import { ProductListComponent }  from './components/shopping-card/product-list/product-list.component';
import { CartListComponent }     from './components/shopping-card/cart-list/cart-list.component';
// Errors
import { Page404NotFoundComponent } from './components/page404-not-found/page404-not-found.component';
import { ClientAreaCardComponent } from './components/client-area-card/client-area-card.component';
import { DevicesListComponent } from './components/client-area-card/devices-list/devices-list.component';
import { NotificationsComponent } from './components/client-area-card/notifications/notifications.component';
import { ProfileInfoComponent } from './components/client-area-card/profile-info/profile-info.component';
import { MeasurementsComponent } from './components/client-area-card/measurements/measurements.component';
import { AFunnyChartComponent } from './components/client-area-card/charts/a-funny-chart/a-funny-chart.component';
import { MeasurementsChartComponent } from './components/client-area-card/charts/measurements-chart/measurements-chart.component';
import { Page401UnauthorizedComponent } from './components/page401-unauthorized/page401-unauthorized.component';
import { Page403ForbiddenComponent } from './components/page403-forbidden/page403-forbidden.component';
import { Page202AcceptedComponent } from './components/page202-accepted/page202-accepted.component';
//import { DetailsComponent } from './components/details/details.component';

@NgModule({
  declarations: [
    AppComponent,
    // Header
    AppHeaderComponent,
    // Home
    HomeCardComponent,
    // Authentication
    AuthenticationCardComponent,
    LoginCardComponent,
    SignupCardComponent,
    // Shopping Cart
    ShoppingCardComponent,
    FiltersComponent,
    ProductListComponent,
    CartListComponent,
    // Errors
    Page404NotFoundComponent,
    // Client Area
    ClientAreaCardComponent,
    DevicesListComponent,
    NotificationsComponent,
    ProfileInfoComponent,
    MeasurementsComponent,
    AFunnyChartComponent,
    MeasurementsChartComponent,
    Page401UnauthorizedComponent,
    Page403ForbiddenComponent,
    Page202AcceptedComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    Ng2TelInputModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

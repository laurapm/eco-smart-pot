import { BrowserModule }           from '@angular/platform-browser';
import { NgModule }                from '@angular/core';
import { LayoutModule }            from '@angular/cdk/layout';
import { MatToolbarModule }        from '@angular/material/toolbar';
import { MatButtonModule }         from '@angular/material/button';
import { MatSidenavModule }        from '@angular/material/sidenav';
import { MatIconModule }           from '@angular/material/icon';
import { MatListModule }           from '@angular/material/list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
import { RegistrationCardComponent }   from './components/authentication-card/registration-card/registration-card.component';
// Shopping Cart
import { ShoppingCardComponent } from './components/shopping-card/shopping-card.component';
import { FiltersComponent }      from './components/shopping-card/filters/filters.component';
import { ProductListComponent }  from './components/shopping-card/product-list/product-list.component';
import { CartListComponent }     from './components/shopping-card/cart-list/cart-list.component';
import { CartItemComponent }     from './components/shopping-card/cart-list/cart-item/cart-item.component';
// Errors
import { Page404NotFoundComponent } from './components/page404-not-found/page404-not-found.component';

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
    RegistrationCardComponent,
    // Shopping Cart
    ShoppingCardComponent,
    FiltersComponent,
    ProductListComponent,
    CartListComponent,
    CartItemComponent,
    // Errors
    Page404NotFoundComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

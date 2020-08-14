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

import { AppHeaderComponent }    from './components/app-header/app-header.component';
import { LoginCardComponent }    from './components/login-card/login-card.component'
import { ShoppingCardComponent } from './components/shopping-card/shopping-card.component';
import { FiltersComponent }      from './components/shopping-card/filters/filters.component';
import { ProductListComponent }  from './components/shopping-card/product-list/product-list.component';
import { CartListComponent }     from './components/shopping-card/cart-list/cart-list.component';
import { CartItemComponent }     from './components/shopping-card/cart-list/cart-item/cart-item.component';

@NgModule({
  declarations: [
    AppComponent,
    AppHeaderComponent,
    LoginCardComponent,
    ShoppingCardComponent,
    FiltersComponent,
    ProductListComponent,
    CartListComponent,
    CartItemComponent
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

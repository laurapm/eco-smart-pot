import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { AppHeaderComponent } from './componentes/app-header/app-header.component';
import { ShoppingCardComponent } from './componentes/shopping-card/shopping-card.component';
import { FiltersComponent } from './componentes/shopping-card/filters/filters.component';
import { ProductListComponent } from './componentes/shopping-card/product-list/product-list.component';
import { CartListComponent } from './componentes/shopping-card/cart-list/cart-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AppHeaderComponent,
    ShoppingCardComponent,
    FiltersComponent,
    ProductListComponent,
    CartListComponent
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

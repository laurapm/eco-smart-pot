import { NgModule }              from '@angular/core';
import { Routes, RouterModule }  from '@angular/router';
import { ShoppingCardComponent } from './components/shopping-card/shopping-card.component'
import { LoginCardComponent }    from './components/login-card/login-card.component'
import { ProductItemComponent } from './components/shopping-card/product-list/product-item/product-item.component';

const routes: Routes = [
  {path:  ''            , redirectTo: 'home', pathMatch: 'full'},
  { path: 'product'    , component: ShoppingCardComponent },
  { path: 'product/:id', component: ProductItemComponent },
  { path: 'login'       , component: LoginCardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

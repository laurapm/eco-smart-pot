import { NgModule }              from '@angular/core';
import { Routes, RouterModule }  from '@angular/router';
// Components
import { HomeCardComponent }           from './components/home-card/home-card.component'
import { ShoppingCardComponent }       from './components/shopping-card/shopping-card.component';
import { ProductItemComponent }        from './components/shopping-card/product-list/product-item/product-item.component';
import { AuthenticationCardComponent } from './components/authentication-card/authentication-card.component';
import { Page404NotFoundComponent }    from './components/page404-not-found/page404-not-found.component';

const routes: Routes = [
  { path: 'home',           component: HomeCardComponent },
  { path: 'product',        component: ShoppingCardComponent },
  { path: 'product/:id',    component: ProductItemComponent },
  { path: 'authentication', component: AuthenticationCardComponent },
  { path: '404',            component: Page404NotFoundComponent },
  { path: '',   redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: '404',  pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

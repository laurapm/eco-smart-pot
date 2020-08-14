import { NgModule }              from '@angular/core';
import { Routes, RouterModule }  from '@angular/router';
import { ShoppingCardComponent } from './components/shopping-card/shopping-card.component'
import { LoginCardComponent }    from './components/login-card/login-card.component'

const routes: Routes = [
  { path: 'product', component: ShoppingCardComponent },
  { path: 'login',   component: LoginCardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

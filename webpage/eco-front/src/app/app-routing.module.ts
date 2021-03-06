import { NgModule }              from '@angular/core';
import { Routes, RouterModule }  from '@angular/router';
// Components
import { HomeCardComponent }            from './components/home-card/home-card.component'
import { ShoppingCardComponent }        from './components/shopping-card/shopping-card.component';
import { AuthenticationCardComponent }  from './components/authentication-card/authentication-card.component';
import { SignupCardComponent }          from './components/authentication-card/signup-card/signup-card.component';
import { LoginCardComponent }           from './components/authentication-card/login-card/login-card.component';
import { ClientAreaCardComponent }      from './components/client-area-card/client-area-card.component';
import { Page202AcceptedComponent }     from './components/page202-accepted/page202-accepted.component';
import { Page401UnauthorizedComponent } from './components/page401-unauthorized/page401-unauthorized.component';
import { Page403ForbiddenComponent }    from './components/page403-forbidden/page403-forbidden.component';
import { Page404NotFoundComponent }     from './components/page404-not-found/page404-not-found.component';

const routes: Routes = [
  { path: 'home',           component: HomeCardComponent },
  { path: 'product',        component: ShoppingCardComponent },
  { path: 'authentication', component: AuthenticationCardComponent,
    children: [
      { path: '', redirectTo: 'signup', pathMatch: 'full'},
      { path: 'signup', component: SignupCardComponent },
      { path: 'login',  component: LoginCardComponent }
    ]
  },
  { path: 'profile',        component: ClientAreaCardComponent },
  { path: '202',            component: Page202AcceptedComponent },
  { path: '401',            component: Page401UnauthorizedComponent },
  { path: '403',            component: Page403ForbiddenComponent },
  { path: '404',            component: Page404NotFoundComponent },
  //{ path: '',   redirectTo: 'home', pathMatch: 'full' },
  //{ path: '**', redirectTo: '404',  pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

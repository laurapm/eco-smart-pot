import { Component }                       from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable }                      from 'rxjs';
import { map, shareReplay }                from 'rxjs/operators';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html',
  styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent {

  toShow: boolean = true;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private tokenStorage: TokenStorageService,
    private breakpointObserver: BreakpointObserver,
    private router: Router) { }

  ngOnInit(): void {
    this.userLoggedIn();
  }

  userLoggedIn(): void {
    let user = this.tokenStorage.getUser();
    this.toShow = user == null;
  }

  closeSession(): void {
    this.tokenStorage.signOut();
    this.toShow = true;
    alert("User signed out");
  }

}

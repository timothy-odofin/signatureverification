import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { UtilService } from 'src/app/services/utilities';
import { Constants } from 'src/app/helpers/messages';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {

  constructor(
    private util: UtilService,
    private router: Router) {}
  private breakpointObserver = inject(BreakpointObserver);


  fullName = this.util.getUserFullname()
  business = this.util.getUserBusinessUnit()

  navigationList = [
    // {name: "Dashboard", route: '/app/dashboard', icon:'dashboard'},
    {name: "Signature Verification", route: '/app/history', icon:'settings'},
    {name: "Signature Data", route: '/app/reports', icon:'dashboard'},
    // {name: "User Management", route: '/app/user-management', icon:'person'},
    // {name: "Settings", route: '/app/settings', icon:'settings'},
  ]

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
  .pipe(
    map(result => result.matches),
    shareReplay()
  );

  logout() {
    this.util.removeFromStore(Constants.LOGIN_USER)
    this.router.navigateByUrl('/')
  }


}

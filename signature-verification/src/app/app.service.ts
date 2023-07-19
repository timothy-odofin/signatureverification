import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { Router } from "@angular/router";
import { Constants } from "./helpers/messages";
import { UtilService } from "./services/utilities";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private isLoggedin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(
    private util: UtilService,
    private router: Router
  ) {
    this.checkLoginStatus()
  }

  checkLoginStatus() {
    let user = this.util.getFromStore(Constants.LOGIN_USER)
    if (user) {
      this.isLoggedin.next(true)
    }
    else {
      this.logout()
    }
  }

  // subscribe to check loginStatus
  loginStatus(): Observable<boolean> {
    return this.isLoggedin.asObservable();
  }

  setLoginStatus(val: boolean): void {
    this.isLoggedin.next(val);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(["/"]);
    this.isLoggedin.next(false);
  }
}

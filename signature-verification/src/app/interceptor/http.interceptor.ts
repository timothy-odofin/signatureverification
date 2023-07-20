import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UtilService } from "../services/utilities";
import { Observable } from "rxjs";
import { Constants } from "../helpers/messages";

@Injectable()
export class AutInterceptor implements HttpInterceptor{

  constructor(private util: UtilService) { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    var token: any = this.util.getFromStore(Constants.LOGIN_USER)?.token;
    var authentication:string = ''

    if (token) {
      authentication = token.token_type + ' ' + token.access_token;
    }
    request = request.clone({
      setHeaders: {
        'Authorization': authentication,
      }
    });
    return next.handle(request)
  }

}

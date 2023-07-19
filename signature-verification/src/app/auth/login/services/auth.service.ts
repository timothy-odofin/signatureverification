import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { Login, Users } from 'src/app/models/auth.model';
import { BaseUrlService } from 'src/app/services/base-url.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url: string = this.base._baseUrl;

  constructor(private http: HttpClient, private base: BaseUrlService) {}

  login(payload: Login): Observable<Users> {
    return this.http
    .post<Users>(this.url + `/auth`, payload)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }
}


import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BaseUrlService {

  _baseUrl: string = 'http://localhost:9599'

  constructor() { }

  /**
  * Contructs proper error message and rethrows error
  *  @param {any} err Error Object.
  * @returns {Observable<never>} Returns an observable.
  */

  errorHandler(err: HttpErrorResponse) {
    let errorMessage: string;
    if (err.error instanceof ErrorEvent) {
      errorMessage = `An error occurred: ${err.error?.message}`;
    } else {
      errorMessage = `${err.statusText}`;
    }
    console.error(err);
    return throwError(() => new Error(errorMessage));
  }
}

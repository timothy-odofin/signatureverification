import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { EventModel, signaturePayload, submitSignaturePayload } from 'src/app/models/event.module';
import { BaseUrlService } from 'src/app/services/base-url.service';

@Injectable({
  providedIn: 'root',
})
export class SignatureService {
  url: string = this.base._baseUrl;

  constructor(private http: HttpClient, private base: BaseUrlService) {}

  getEventSource(page:number = 0, size:number = 50): Observable<EventModel> {
    return this.http
    .get<EventModel>(this.url + `/account/event-sources?page=${page}&size=${size}`)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }

  getReportData(): Observable<any> {
    return this.http
    .get<any>(this.url + `/account/event-sources/summary`)
    .pipe(catchError((err) => this.base.errorHandler(err)));

  }

  validateSignature(payload: signaturePayload): Observable<any> {
    return this.http
    .post<any>(this.url + `/account/signature/validate`, payload)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }

  getEventSourceById(pid:string): Observable<EventModel> {
    return this.http
    .get<EventModel>(this.url + `/account/signature/retrieve?event-pid=${pid}`)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }

  submitSignature(params:string, payload: submitSignaturePayload): Observable<any> {
    return this.http
    .post<any>(this.url + `/account/event-sources/update/${params}`, payload)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }



  getCurrencies(): Observable<String> {
    return this.http
    .get<String>(this.url + `/account/currencies`)
    .pipe(catchError((err) => this.base.errorHandler(err)));
  }


}

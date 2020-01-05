import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Match } from '../model/match';

@Injectable({
   providedIn: 'root'
})
export class MatchService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/match';
  }

  public findMatch(countries: string[], devices: string[]): Observable<Map<string, number>> {
    let params = new HttpParams();
    for (const country of countries) {
        params = params.append('countries', country)
    }
    for (const device of devices) {
        params = params.append('devices', device);
    }
    return this.http.get<Map<string, number>>(this.url, { params: params });
  }
}

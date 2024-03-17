import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpCustomOptions } from '../../types';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private httpClient: HttpClient
  ) { }
  get<T>(url: string, options: HttpCustomOptions): Observable<T> {
    return this.httpClient.get<T>(url, options) as Observable<T>
  }
  post<T>(url: string, options: HttpCustomOptions): Observable<T> {
    return this.httpClient.post<T>(url, options) as Observable<T>
  }
}

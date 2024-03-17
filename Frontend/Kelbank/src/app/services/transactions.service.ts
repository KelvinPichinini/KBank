import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  constructor(private apiService:ApiService) { }
  getTransactionById = (url: string, params): Observable<any> => {
    return this.apiService.get(url, params)
}

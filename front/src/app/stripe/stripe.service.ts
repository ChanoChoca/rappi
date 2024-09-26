import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StripeService {

  private apiUrl = `${environment.API_URL}`

  constructor(private http: HttpClient) {}

  createPaymentIntent(data: any) {
    return this.http.post<{ clientSecret: string }>(
      `${this.apiUrl}/create-payment-intent`,
      data
    ).toPromise();
  }
}

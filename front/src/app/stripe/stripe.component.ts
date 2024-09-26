import { Component, OnInit } from '@angular/core';
import { StripeService } from './stripe.service';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from "../environments/environment";

@Component({
  selector: 'app-stripe',
  standalone: true,
  imports: [],
  templateUrl: './stripe.component.html',
  styleUrl: './stripe.component.css'
})
export class StripeComponent implements OnInit {
  public stripe: any;
  public elements: any;
  public card: any;
  public clientSecret: string | undefined;

  amount: number = 1000; // Monto en centavos
  productName: string = 'Nombre del Producto';
  email: string = 'cliente@example.com';

  constructor(private stripeService: StripeService) {}

  ngOnInit(): void {
    this.initializePayment();
  }

  async initializePayment() {
    this.stripe = await loadStripe(`${environment.STRIPE_PUBLIC_KEY}`); // Cambia por tu clave pública
    const response = await this.stripeService.createPaymentIntent({
      amount: this.amount,
      productName: this.productName,
      email: this.email,
    });

    this.clientSecret = response!.clientSecret;

    const elementsOptions = {
      clientSecret: this.clientSecret,
    };

    this.elements = this.stripe.elements(elementsOptions);
    this.card = this.elements.create('card');
    this.card.mount('#card-element');
  }

  async handleSubmit(event: Event) {
    event.preventDefault();

    const { error } = await this.stripe.confirmCardPayment(this.clientSecret, {
      payment_method: {
        card: this.card,
        billing_details: {
          email: this.email,
        },
      },
    });

    if (error) {
      console.error(error.message);
    } else {
      // El pago se ha realizado con éxito
      console.log('Pago realizado con éxito');
    }
  }
}

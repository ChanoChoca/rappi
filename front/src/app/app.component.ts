import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {StripeComponent} from "./stripe/stripe.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, StripeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'front';
}

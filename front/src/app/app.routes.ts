import { Routes } from '@angular/router';
import {StripeComponent} from "./stripe/stripe.component";
import {HomeComponent} from "./home/home.component";

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  { path: 'payment',
    component: StripeComponent
  }
];

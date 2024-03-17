import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    {
        path: 'Home',
        component:HomeComponent
    },
    {
        path: 'Login',
        component: HomeComponent
    },
    {
        path: 'Profile',
        component:HomeComponent
    },
    {
        path: 'Dashboard',
        component:HomeComponent
    }
];

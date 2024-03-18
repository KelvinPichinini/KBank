import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutComponent } from './layout/layout.component';


export const routes: Routes = [
    {
        path:'',
        redirectTo:'login',
        pathMatch:'full',
    },
    {
        path:'login',
        component: LoginComponent
    },
    {
        path:'register',
        component:RegisterComponent
    },
    {
        path:'dashboard',
        component:DashboardComponent
    }

];

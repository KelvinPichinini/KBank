import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserInterface } from '../user.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  fb = inject(FormBuilder);
  http = inject(HttpClient);

  constructor( private router: Router){
  }

  form = this.fb.nonNullable.group({
    email:["", Validators.required],
    password:["", Validators.required],
    firstName:["", Validators.required],
    lastName:["", Validators.required],
    document:["", Validators.required],
  })

  onSubmit(): void {
    this.http.post<UserInterface>('http://localhost:8080/users',
    this.form.getRawValue(),
  
    
    ).subscribe(response => {
      console.log(response.email)
      this.router.navigateByUrl('/login')
    })
  }

}

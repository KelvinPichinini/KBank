import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserInterface } from '../user.interface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginObj : Login;

  constructor(private http: HttpClient, private router: Router){
    this.loginObj = new Login;
  }

  onLogin() {
    this.http.post('http://localhost:8080/auth/login',this.loginObj).subscribe((res:any)=>{
      if(res.token) {
        localStorage.setItem('userToken',res.token)
        alert("Logged in successfully");
        this.router.navigateByUrl('/dashboard')
      } else {
        alert("Usuário ou senha incorretos")
      }
    })
  }

}

export class Login {
  email:string;
  password:string;
  constructor(){
    this.email = "";
    this.password = ""
  }
}

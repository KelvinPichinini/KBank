import {Component, OnInit, inject} from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterLinkActive,RouterOutlet,HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  authService = inject(AuthService)
  constructor(private router: Router){}

  ngOnInit(): void {
      
  }
  title = 'Kelbank';
  showFiller = false;
  logout():void {
    this.authService.currentTokenSig.set(null);
    localStorage.removeItem("userToken")
    this.router.navigateByUrl('/login')
    console.log('logout');
  }
  
}

import {Component, OnInit} from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterLinkActive,RouterOutlet,HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  ngOnInit(): void {
      
  }
  title = 'Kelbank';
  showFiller = false;
  logout():void {
    console.log('logout');
  }
  
}

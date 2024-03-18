import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{

  balance:Number = 0;
  constructor(private http: HttpClient) {
    
  }
  ngOnInit(): void{
    this.getUserBalance();

  }
  getUserBalance() {
    console.log("eu")
    this.http.get("http://localhost:8080/users/balance").subscribe((res:any) => {
      console.log(res)
      this.balance = res;

    }, error => {
      console.log(error.message)
    })
  }

}

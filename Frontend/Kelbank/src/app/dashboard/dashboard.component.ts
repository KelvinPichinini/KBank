import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{

  balance:Number = 0;
  sendTransfer: SendTransfer;
  methods: String[] = ["PIX", "BOLETO", "TED"]
  constructor(private http: HttpClient) {
    this.sendTransfer = new SendTransfer();

    
  }
  ngOnInit(): void{
    this.getUserBalance();

  }
  getUserBalance() {
    this.http.get("http://localhost:8080/users/balance").subscribe((res:any) => {
      console.log(res)
      this.balance = res;
    }, error => {
      console.log(error.message)
    })
  }

  onSendTransfer() {

    this.http.post('http://localhost:8080/transaction',this.sendTransfer).subscribe((res:any)=>{
      if(res.sender) {
        this.balance = res.sender.balance
      }
    }, error => { console.log(error.error)})
  }


}

export class SendTransfer {
  receiverEmail:string;
  amount:number;
  constructor(){
    this.receiverEmail = "";
    this.amount = 0;
  }
}



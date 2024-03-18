import { Injectable, signal } from "@angular/core";

@Injectable({
    providedIn:'root'
})
export class AuthService {
    currentTokenSig = signal<string | undefined | null>(localStorage.getItem("userToken"))
}
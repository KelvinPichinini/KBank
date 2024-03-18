export interface UserInterface {
    id: string,
    firstName: string,
    lastName: string,
    document: string,
    email: string,
    password: string,
    balance: number,
    enabled: boolean,
    credentialsNonExpired: boolean,
    accountNonExpired: boolean,
    authorities: [
        {
            authority: string
        }
    ],
    username: string,
    accountNonLocked: boolean
}
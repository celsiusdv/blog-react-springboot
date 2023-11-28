import { User } from "./user";

export interface LoginResponse{
    user?:User;
    accessToken?:string;
    refreshToken?:string;
}
export interface Token{
    accessToken:string;
    refreshToken:string;
}
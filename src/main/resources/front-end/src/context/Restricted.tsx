import React from "react";
import { LoginResponse } from "../models/login-response";
import { RestrictedProps, UserAuth } from "../models/types";
import { Role } from "../models/user";
import { useAuthContext } from "./AuthProvider";

const Restricted = ({authorities, children}:RestrictedProps) => {
    const { auth }: UserAuth = useAuthContext();
    const { user }: LoginResponse = auth;
    let isAuthority:boolean=false;
    //check if authority exist and whether the case is true, render the children
    const renderChild =():JSX.Element | undefined =>{
        user?.authorities?.forEach((role: Role) => {
            authorities.includes(role.authority!)
                ? isAuthority = true : isAuthority = false;
        });
        if(isAuthority) return <div>{children}</div>
    }
    return (    
        <React.Fragment>
            {renderChild()}
        </React.Fragment>
     );
}
 
export default Restricted;
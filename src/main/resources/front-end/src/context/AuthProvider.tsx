import { createContext, useContext, useState } from "react";
import { User } from "../models/user";

//this wraps all the information we need to send to its children components
//the AuthContext value is filled in Login.tsx component
const AuthContext = createContext<ValueContext| undefined>(undefined);
export const AuthProvider = ({ children }: AuthContextProps) => {
    const [auth, setAuth] = useState<User>({});
    if(AuthContext === undefined)throw new Error("no value found for AuthContext.Provider");
    return (
        <AuthContext.Provider value={{ auth, setAuth }}>{/* load user with the credentials */}
            {children}
        </AuthContext.Provider>
    )
}
export default AuthContext;

export const useAuthContext = () =>{//get the credentials
    const user =useContext<ValueContext|undefined>(AuthContext);
    if(user === undefined){
        throw new Error("useAuthContext is undefined");
    }
    return user;
}




import { FormEvent, useContext, useState } from "react";
import "./navbar.css";
import axios from "axios";
import { User } from "../../models/user";
import AuthContext from "../../context/AuthProvider";

const Login = () => {
    const authContext  = useContext<ValueContext | undefined>(AuthContext);
    const [email,setEmail]=useState<string>("");
    const [password,setPassword]=useState<string>("");

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) =>{
        event.preventDefault();
        let body: User = { email, password };
        try{        //wait until the fetching is complete          
            const response = await axios.post("http://localhost:8080/authentication/api/login",
            body,//object to send to the server
                {
                    headers: { 
                        "Accept": "application/json",
                        "Content-Type": "application/json" ,
                        "Access-Control-Allow-Credentials":true
                    },
                }
            );
            body=response.data;
            authContext?.setAuth(body);
            console.log(body,"\nresponse from the server");

        } catch (error:unknown) {
            if(axios.isAxiosError(error)){
                if(error.response?.status === 500){
                    console.log("error in client side while trying to log in with credentials: "
                    ,body,"\n",error.response.data);//error.response.data.message
                }
            }
        }
    }
    return (
        <div className="login">
            <form onSubmit={(e) =>handleSubmit(e)}>
                <input type="text" onChange={(event) =>setEmail(event.target.value)}/>
                <input type="text" onChange={(event) =>setPassword(event.target.value)}/>
                <button>Log in</button>
            </form>
        </div>
    );
};

export default Login;
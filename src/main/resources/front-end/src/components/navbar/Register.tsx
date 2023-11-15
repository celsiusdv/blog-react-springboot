import { Input } from "@nextui-org/react";
import { ChangeEvent, FormEvent, useState } from "react";
import { User } from "../../models/user";

const Register = () => {
    ///validators
    const [name,setName]=useState<string>("");
    const [requiredUserName,setRequiredUserName]=useState<boolean>(true);
    const handleRequiredUserName=(event:ChangeEvent<HTMLInputElement>)=>{
        if(event.target.value.length > 0){
            setRequiredUserName(false);
            setName(event.target.value);
        }else setRequiredUserName(true);
    }

    const [email,setEmail]=useState<string>("");
    const [requiredEmail,setRequiredEmail]=useState<boolean>(true);
    const handleRequiredEmail=(event:ChangeEvent<HTMLInputElement>)=>{
        if(event.target.value.length > 0){
            setRequiredEmail(false);
            setEmail(event.target.value);
        }else setRequiredEmail(true);
    }

    const [password,setPassword]=useState<string>("");
    const [requiredPassword,setRequiredPassword]=useState<boolean>(true);
    const handleRequiredPassword=(event:ChangeEvent<HTMLInputElement>)=>{
        if(event.target.value.length > 0){
            setRequiredPassword(false);
            setPassword(event.target.value);
        }else setRequiredPassword(true);
    }

    //submit user object
    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const user: User = { name, email, password };
        console.log("saving... ", user);

        fetch('http://localhost:8080/authentication/api/register', {//send user to spring-boot API
            method: 'POST',
            headers: { 
                'Accept': 'application/json',
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(user)//saving the entire blog as a string

        }).then((response) => { return response.json();
        }).then((data) =>{
            console.log(data," returning from the server");
        }).catch(e =>{
            console.error(e);
        });
    }
    return (
        <div className="w-full flex flex-col gap-4 place-items-center p-8">{/* main panel */}
            <h2>Register User</h2>
            <form key={"sm"} className="flex w-4/12 flex-col md:flex-nowrap mb-6 md:mb-0 gap-4"
                onSubmit={(e) =>handleSubmit(e)}>

                <Input size={"md"} type="text" label="User Name" placeholder="Enter your name"
                 isRequired={requiredUserName} onChange={(event) =>handleRequiredUserName(event)}/>

                <Input size={"md"} type="email" label="Email" placeholder="Enter your email"
                 isRequired={requiredEmail} onChange={(event) =>handleRequiredEmail(event)}/>

                 {/* TODO:change type to password later */}
                <Input size={"md"} type="text" label="Password" placeholder="Enter your password"
                 isRequired={requiredPassword} onChange={(event) =>handleRequiredPassword(event)}/>

                 <button>submit</button>
            </form>

        </div>
    );
};

export default Register;
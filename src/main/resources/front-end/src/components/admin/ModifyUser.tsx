import { useLocation, useParams } from "react-router-dom";
import { userId } from "../../models/types";
import useInterceptor from "../../hooks/useInterceptor";
import { User } from "../../models/user";
import { FormEvent, useEffect, useState } from "react";
import axios from "axios";

const ModifyUser = () => {
    const axiosPrivate = useInterceptor();
    const { id } = useParams<userId>();//get the route params '/modify-user/:id' from App.tsx, then the value from AdminPane.tsx
    const location= useLocation();
    //get the query params from AdminPane.tsx set in the action buttons
    const queryAction:string|null = new URLSearchParams(location.search).get('action');
    const queryUser:string|null = new URLSearchParams(location.search).get('user');
    const[name, setName]=useState<string>("");
    const[email,setEmail]=useState<string>("");
    const[password,setPassword]=useState<string>("");
    let user:User={}
    useEffect(() => {
        const getUser = async () => {
            try {
                const response = await axiosPrivate.get<User>(`/api/users/user/${queryUser}`);
                console.log(response.data, " get user by email");
                setName(response.data.name!);
                setEmail(response.data.email!);
            } catch (error) {
                //handle errors & todo: set value in the inputs//
                console.log(error);
            }
        };
        getUser();
    }, []);//this function avoid infinite loop after fething data

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        user={userId:Number(id),name,email,password}
        console.log(user);
        try {
            if (queryAction === "update") {
                const response = await axiosPrivate.put<User>(`/api/users/user/${id}`,user);
                console.log(response.data, " udpated user");
            } else {
                await axiosPrivate.delete<User>(`/api/users/user/${id}`);
                console.log("clicked in delete");
            }
        } catch (error) {
            if(axios.isAxiosError(error))console.log(error.message);
            console.log(error);
        }
    }
    return ( 
        <div>
            {queryAction} user with id: {id} and email: {queryUser}
            <form onSubmit={(e) =>handleSubmit(e)}>
                <br />
                <input type="text" value={name} onChange={(e) => {
                    setName(e.target.value);
                }} />
                <br />
                <input type="email" value={email} onChange={(e) => {
                    setEmail(e.target.value);
                }} />
                <br />
                {queryAction ==="update" && <input type="text" value={password} onChange={(e) => {
                    setPassword(e.target.value);
                }} />}
                <br />
                <button >{queryAction}</button>
            </form>
        </div>
    );
}
 
export default ModifyUser;
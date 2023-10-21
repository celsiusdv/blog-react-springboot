import { useState, useEffect } from "react";
import { User } from "../models/user";

export const fetchUser = (url: string): any => {
    const [users, setUsers] = useState<User[] | null>(null);
    const [isPending, setIsPending] = useState<boolean>(true);
    const [error, setError] = useState<DOMException | null>(null);

    useEffect(() => {
        const abortFetch: AbortController = new AbortController();
        const signal: AbortSignal = abortFetch.signal;

        fetch(url, { signal })
            .then(response => {
                return response.json();//wait untill this variable it's completely filled to pass in the next then(data =>
            })
            .then(data => {//run this method once the first response it's completely filled in the return from above
                setUsers(data);//fill setUsers() once the return of the first .then is completed
                setIsPending(false);//once the data is completely filled, set the template loading to false
                setError(null);//if the data is completely filled, set the error template to null
            })
            .catch(e => {
                if (e.name === "AbortError") {
                    console.log("fetch aborted, unmount component");
                } else {
                    //if the data is not found or there is another kind of error, set the template loading to false and replace it with error
                    setIsPending(false);
                    setError(e.message);
                }
            });
        return () => abortFetch.abort();//abort the fetching to unmount the component after switching between components in the page
    
    }, [url]);

    return { users, isPending, error };//return from the useFetch
}
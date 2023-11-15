type FetchedData = {//typechecking for users from useFetch
    data: User[];
    isLoading: Boolean;
    error: string | unknown;
};
//typechecking specifically for the table in AdminPane.tsx because the NextUI table component can't hold
//directly the array of roles
type UserTable={
    userId:number;
    name:string;
    email:string;
    role:string;
}

//------types for Authentication///////////////////////
type ValueContext = {//typechecking to set props value in AuthContext.Provider
    /*User model directly form the login response with token and the modified role list
    * to understand the modified role list, check the java class UserDetailsServiceImp in the method getAuthorities*/
    auth: User;
    setAuth: React.Dispatch<React.SetStateAction<User>>;
};

type AuthContextProps = {//props to set in AuthProvider.tsx
    children: React.ReactNode;
};

//////////////////////////////////////////////
type column = {//typechecking for columns in AdminPane component
    name: string,
    uid: string;
};
type userId = {// typechecking for useParams in ModifyUser component
    id: string;
};
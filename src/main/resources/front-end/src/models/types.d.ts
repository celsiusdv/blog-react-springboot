import { LoginResponse } from "./login-response";
import { Role } from "./user";

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
type UserAuth = {//typechecking to set props value in AuthContext.Provider
    auth: LoginResponse;
    setAuth: React.Dispatch<React.SetStateAction<LoginResponse>>;
};

type AuthContextProps = {//props to set in AuthProvider.tsx
    children: React.ReactNode;
};
type RoleProps={// props to set in RequireAuth.tsx
    authorities:string[];
}
type RestrictedProps={//props to set in Restricted.tsx
    authorities:string[];
    children: React.ReactNode;
}

//////////////////////////////////////////////
type column = {//typechecking for columns in AdminPane component
    name: string,
    uid: string;
};
type userId = {// typechecking for useParams in ModifyUser component
    id: string;
};

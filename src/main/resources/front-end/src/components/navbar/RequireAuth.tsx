import { useLocation, Outlet, Navigate } from "react-router-dom";
import { useAuthContext } from "../../context/AuthProvider";

import { Role, User } from "../../models/user";
import React from "react";

type RoleProps={
    authorities:string[];
}
const RequireAuth = ({ authorities }: RoleProps) => {
    const { auth }: ValueContext = useAuthContext();
    const user: User = auth;
    const location = useLocation();
    let isRole: boolean = false;
    const authorizeRender = () => {
        try {
            user.authorities!.forEach((role: Role) => {
                if (isRole === true) return;
                for (let j = 0; j < authorities.length; j++) {
                    //compare the array of roles from the authenticated user against the RoleProps
                    if (role.authority === authorities[j]) {
                        console.log("role detected: " + role.authority);
                        isRole = true;
                    }
                }
                console.log("is ", role.authority, " in props? ", isRole);
            });
        } catch (error) {
            console.error("authentication not found, redirecting to login")
            /*
            * if any non aunthenticated user reach the endpoint, the user.authorities will be empty
            * therefore an error will be thrown for a redirection to the login page
            */
            return <Navigate to="/login" state={{ from: location }} replace />;
        }
        if (isRole === true) return <Outlet />;//render the child component inside <RequireAuth> in App nested routes
        else return (user !== null) && <h1> unauthorized </h1>;//return this component if the user exist but without the required role
    };

    return (
        <React.Fragment>
            {authorizeRender()}
        </React.Fragment>
    );
};
/*         user.authorities?.find((role:Role) => authorities?.includes(role.authority!))
            ? <Outlet />//the item to be rendered according to the role in the App component within the routes
            : user !== null
                ? <Navigate to="/unauthorized" state={{ from: location }} replace />
                : <Navigate to="/login" state={{ from: location }} replace /> */


export default RequireAuth;
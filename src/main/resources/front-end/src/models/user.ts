/*
* the backend response give us a user with roles but
* without nested privileges, the merging with the role and nested privileges occur in
* the java class UserDetailsServiceImp in the method getAuthorities()
*/
export interface User{
    userId?:number;
    name?:string;
    email?:string;
    password?:string;
    authorities?:Array<Role>;
    token?:string;
}
export interface Role{
<<<<<<< HEAD
    roleId?:number;
    authority?:string;
    privileges?:Array<Privilege>;
}
export interface Privilege{
    privilegeId?:number;
    privilege?:string;
}
=======
    authority?:string;
}
>>>>>>> authBranch

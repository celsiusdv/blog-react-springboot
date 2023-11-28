<<<<<<< HEAD
import { useEffect, useState } from "react";
import useInterceptor from "../../helpers/useInterceptor";
import { User } from "../../models/user";
import axios from "axios";
=======
import Restricted from "../../context/Restricted";
>>>>>>> authBranch


const Home = () => {
    return ( 
        <div>
            <h1>home works!!!!</h1>
<<<<<<< HEAD
=======
            <Restricted authorities={["ADMIN_read"]}>
                <h2> admin section</h2>
            </Restricted>
            <Restricted authorities={["ADMIN_read","USER_read"]}>
                <h2> admin or user section</h2>
            </Restricted>
                <h2> vistor section</h2>
            
>>>>>>> authBranch
        </div>
     );
}
 
export default Home;
import { useAuthContext } from "../../context/AuthProvider";
import Restricted from "../../context/Restricted";
import { UserAuth } from "../../models/types";



const Home = () => {
    const { auth }: UserAuth = useAuthContext();
    return ( 
        <div>
            <h1>home works!!!!</h1>
            <Restricted authorities={["ADMIN_read"]}>
                <h2> admin section</h2>
            </Restricted>
            <Restricted authorities={["USER"]}>
                <h2> user section</h2>
            </Restricted>
                <h2> vistor { auth.isLoggedIn === true ? <span>logged in</span> : <span>not logged in</span>}</h2>
            
        </div>
     );
}
 
export default Home;
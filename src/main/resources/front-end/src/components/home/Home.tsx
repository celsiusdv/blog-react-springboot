import Restricted from "../../context/Restricted";


const Home = () => {
    return ( 
        <div>
            <h1>home works!!!!</h1>
            <Restricted authorities={["ADMIN_read"]}>
                <h2> admin section</h2>
            </Restricted>
            <Restricted authorities={["ADMIN_read","USER_read"]}>
                <h2> admin or user section</h2>
            </Restricted>
                <h2> vistor section</h2>
            
        </div>
     );
}
 
export default Home;
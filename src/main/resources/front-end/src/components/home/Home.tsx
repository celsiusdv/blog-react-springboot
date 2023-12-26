import { useAuthContext } from "../../context/AuthProvider";
import Restricted from "../../context/Restricted";
import { useFetch } from "../../hooks/useFetch";
import { Blog } from "../../models/blog";
import { FetchedBlogs, UserAuth } from "../../models/types";
import BlogList from "../blog/BlogList";



const Home = () => {
    const { auth }: UserAuth = useAuthContext();
    const {data:blogs}:FetchedBlogs=useFetch("/api/blogs/blogs");
    return ( 
        <div>
            <BlogList blogs={blogs}/>
        </div>
     );
}
 
export default Home;

{/* <h1>home works!!!!</h1>
<Restricted authorities={["ADMIN_read"]}>
    <h2> admin section</h2>
</Restricted>
<Restricted authorities={["USER"]}>
    <h2> user section</h2>
</Restricted>
<h2> vistor { auth.isLoggedIn === true ? <span>logged in</span> : <span>not logged in</span>}</h2> */}
import { Button, Input } from "@nextui-org/react";
import { useAuthContext } from "../../context/AuthProvider";
import Restricted from "../../context/Restricted";
import { useFetch } from "../../hooks/useFetch";
import { Blog } from "../../models/blog";
import { FetchedBlogs, UserAuth } from "../../models/types";
import BlogList from "../blog/BlogList";
import { SearchIcon } from "../icons/SearchIcon";
import "./home.css";


const Home = () => {
    const { auth }: UserAuth = useAuthContext();
    const {data:blogs}:FetchedBlogs=useFetch("/api/blogs/blogs");
    const search =() =>{
        
    }
    return ( 
        <div className="home" tabIndex={0}>
            <Input isClearable className="w-full sm:max-w-[44%] m-5" placeholder="Search specific blog..."
                startContent={<SearchIcon />} onKeyDown={ (e) => {
                    e.key === "Enter" && console.log(e.key);
                }} />
            
            <BlogList blogs={blogs} />
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
import { Button, Input, Tooltip } from "@nextui-org/react";
import { useAuthContext } from "../../context/AuthProvider";
import Restricted from "../../context/Restricted";
import { useFetch } from "../../hooks/useFetch";
import { Blog } from "../../models/blog";
import { FetchedBlogs, UserAuth } from "../../models/types";
import BlogList from "../blog/BlogList";
import { SearchIcon } from "../icons/SearchIcon";
import "./home.css";
import { useEffect, useRef, useState } from "react";


const Home = () => {
    const { auth }: UserAuth = useAuthContext();
    const [url,setUrl]=useState("/api/blogs/blogs");
    const {data:blogs}:FetchedBlogs=useFetch(url);
    let browse=useRef<string>("");
    const search =() =>setUrl(`/api/blogs/blogs/${browse.current}`);
// TODO: ADD PAGINATOR
    
    return ( 
        <div className="home" tabIndex={0}>
            <Tooltip showArrow={true} content="click in the search icon or press enter to search">
                <Input className="w-full sm:max-w-[44%] m-7 search-input" isClearable placeholder="Search blogs"
                    startContent={<SearchIcon className={"search-icon"}
                                            onClick={() => search()} />}
                                            onKeyDown={(e) => e.key === "Enter" && search()}
                                            onChange={(e) => browse.current = e.target.value} />
            </Tooltip>

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
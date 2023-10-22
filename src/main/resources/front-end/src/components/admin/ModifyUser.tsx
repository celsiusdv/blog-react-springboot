import { useLocation, useParams } from "react-router-dom";

type userId ={id:string;}

const ModifyUser = () => {
    const { id } = useParams<userId>();
    const location= useLocation();
    const queryParam:string|null = new URLSearchParams(location.search).get('action');
    return ( 
        <div>
            showing pane to delete or modify user with id: {id} with action {queryParam}
        </div>
     );
}
 
export default ModifyUser;
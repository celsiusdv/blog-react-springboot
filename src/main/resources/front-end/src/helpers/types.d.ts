type FetchedUser = { data: User[]; isLoading: Boolean; error: string; };//typechecking after getting users from useFetch
type column = { name: string, uid: string; };//typechecking for columns in AdminPane component
type userId = { id: string; };// typechecking for useParams in ModifyUser component
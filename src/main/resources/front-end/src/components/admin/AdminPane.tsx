import { Input, Table, TableBody, TableCell, TableColumn, TableHeader, TableRow, Tooltip, User as UserDom } from "@nextui-org/react";
import React from "react";
import { DeleteIcon } from "../icons/DeleteIcon";
import { EditIcon } from "../icons/EditIcon";
import { EyeIcon } from "../icons/EyeIcon";
import { User as ModelUser } from "../../models/user";
import { SearchIcon } from "../icons/SearchIcon";
import { Link } from "react-router-dom";
import { useFetch } from "../../helpers/useFetch";


//values for the table column

const columns:column[] = [
  {name: "NAME", uid: "name"},
  /* {name: "ROLE", uid: "role"}, */
  {name: "ACTIONS", uid: "actions"},
];
const AdminPane = () => {
    const {data:users, isLoading, error}:FetchedUser=useFetch("http://localhost:8080/users/api/user");
    const renderCell = React.useCallback((user: ModelUser, columnKey: React.Key) => {
        const cellValue = user[columnKey as keyof ModelUser];//user.ts model attributes used as column names

        switch (columnKey) {
            case "name":
                return (
                    <UserDom avatarProps={{ radius: "lg", src: "" }} description={user.email}
                        name={cellValue} >
                        {user.name}
                    </UserDom>
                );

            case "actions":
                return (
                    <div className="relative flex items-center gap-3">
                        <Tooltip content="Details">
                            <span className="text-lg text-default-400 cursor-pointer active:opacity-50">
                                <EyeIcon />
                            </span>
                        </Tooltip>
                        <Tooltip content="Edit user">
                            <span className="text-lg text-default-400 cursor-pointer active:opacity-50">
                                <Link to={{ pathname:`/modify-user/${user.userId}`, search:'?action=update',}}>
                                    <EditIcon onClick={
                                        () => {
                                            console.log("user name: ", user.name, "id: " + user.userId);
                                        }
                                    } />
                                </Link>
                            </span>
                        </Tooltip>
                        <Tooltip color="danger" content="Delete user" >
                            <span className="text-lg text-danger cursor-pointer active:opacity-50">
                                {/* using path params and query params to use in ModifyUser component */}
                                <Link to={{ pathname:`/modify-user/${user.userId}`, search:'?action=delete',}}>
                                    <DeleteIcon onClick={
                                        () => {
                                            console.log("user name: ",user.name, "id: "+user.userId);
                                        }
                                    } />
                                </Link>

                            </span>
                        </Tooltip>
                    </div>
                );
            default:
                return cellValue;
        }
    }, []);

    return (
        <div className="flex flex-col gap-4">
            <div className="flex justify-between gap-3 items-end mt-2 ml-4">
                <Input isClearable className="w-full sm:max-w-[44%]" placeholder="Search by email..."
                    startContent={<SearchIcon />} />
            </div>
            <Table color="secondary" selectionMode="single" aria-label="table for managing users">

                <TableHeader columns={columns}>
                    {(column) => (
                        <TableColumn key={column.uid} align={column.uid === "actions" ? "center" : "start"}>
                            {column.name}
                        </TableColumn>
                    )}
                </TableHeader>

                <TableBody items={users} >
                    {(item: ModelUser) => (
                        <TableRow key={item.userId}>
                            {(columnKey: React.Key) =>(
                                <TableCell>{renderCell(item, columnKey)}</TableCell>
                            )}
                        </TableRow>
                    )}
                </TableBody>
            </Table>
            
        </div>
    );
};

export default AdminPane;
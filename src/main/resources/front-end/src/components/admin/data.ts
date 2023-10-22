import { User } from "../../models/user";

type column={name:string, uid:string}

const columns:column[] = [
  {name: "NAME", uid: "name"},
  /* {name: "ROLE", uid: "role"}, */
 /*  {name: "STATUS", uid: "status"}, */
  {name: "ACTIONS", uid: "actions"},
];

const users:User[] = [
  {
    userId: 1,
    name: "Tony Reichert",
    email: "tony.reichert@example.com",
    password:"sadfasdfas"
  },
  {
    userId: 2,
    name: "Zoey Lang",
    email: "zoey.lang@example.com",
    password:"sadfasdfas"
  },
  {
    userId: 3,
    name: "Jane Fisher",
    email: "jane.fisher@example.com",
    password:"sadfasdfas"
  },
  {
    userId: 4,
    name: "William Howard",
    email: "william.howard@example.com",
    password:"sadfasdfas"
  },
  {
    userId: 5,
    name: "Kristen Copper",
    email: "kristen.cooper@example.com",
    password:"sadfasdfas"
  },
];

export {columns, users};
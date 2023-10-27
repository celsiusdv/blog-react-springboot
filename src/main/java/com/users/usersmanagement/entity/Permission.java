package com.users.usersmanagement.entity;

import javax.persistence.*;

@Table(name = "permissions")
@Entity(name="Permission")
public class Permission{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    private Integer permissionId;
    private String privilege;

    public Permission(){}
    //overloading constructor to fetch specific column data in jpql query in PermissionRepository
    public Permission(String privilege){ this.privilege=privilege; }

    public void setPrivilege(String privilege){ this.privilege=privilege; }
    public String getPrivilege(){ return this.privilege; }

    @Override
    public String toString() {
        return this.privilege;
    }
}

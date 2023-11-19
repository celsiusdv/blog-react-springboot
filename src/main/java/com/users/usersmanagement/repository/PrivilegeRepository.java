package com.users.usersmanagement.repository;

import com.users.usersmanagement.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PrivilegeRepository extends JpaRepository<Privilege,Integer> {
    @Query("SELECT new Privilege(p.privilegeId,p.privilege) FROM  Privilege p")
    Set<Privilege> getAdminPermissions();

    @Query(value="SELECT new Privilege(p.privilegeId,p.privilege) FROM  Privilege p WHERE privilege_id in (1,3)")//1 and 3 are the privileges id in the table
    Set<Privilege> getUserPermissions();
}

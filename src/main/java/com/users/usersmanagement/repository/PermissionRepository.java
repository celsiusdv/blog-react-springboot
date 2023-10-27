package com.users.usersmanagement.repository;

import com.users.usersmanagement.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    @Query("SELECT new Permission(p.privilege) FROM  Permission p")
    Set<Permission> getOwnerPermissions();

    @Query(value="SELECT new Permission(p.privilege) FROM  Permission p WHERE permission_id in (1,3)")
    Set<Permission> getUserPermissions();
}

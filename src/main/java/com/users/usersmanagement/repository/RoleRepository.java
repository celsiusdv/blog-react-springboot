package com.users.usersmanagement.repository;


import com.users.usersmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("SELECT r FROM  Role r WHERE r.authority=?1")
    Optional<Role> findByRoleName(String role);
}

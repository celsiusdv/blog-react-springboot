package com.users.usersmanagement.repository;

import com.users.usersmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //auto-generate a custom search SQL query
    Optional<User> findUserByEmail(String email);//custom Query
    @Transactional
    @Modifying //annotation to tell that anything mapped to the table is for INSERT/UPDATE/DELETE queries
    @Query("DELETE FROM User u WHERE u.userId=?1")
    void deleteUserById(Integer userId);
}

package com.users.usersmanagement.repository;

import com.users.usersmanagement.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    @Query("SELECT new RefreshToken(t.refreshTokenId, t.refreshToken, t.expiryDate, t.user)" +
            " FROM  RefreshToken t WHERE t.refreshToken=:token")
    Optional<RefreshToken> findRefreshToken(@Param("token") String token);
}

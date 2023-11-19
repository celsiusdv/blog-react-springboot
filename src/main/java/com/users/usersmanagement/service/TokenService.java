package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.RefreshToken;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.repository.RefreshTokenRepository;
import com.users.usersmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    //6- generate an access token for the logged user if it is successfully authenticated
    public String createJwtAccessToken(Authentication user){
        Instant now = Instant.now();
        Instant thirtyMinutes=ZonedDateTime.now().plusMinutes(30).toInstant();
        String role = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(user.getName())
                .expiresAt(thirtyMinutes)
                .claim("roles", role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public RefreshToken createUUIDRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                ZonedDateTime.now().plusMinutes(1440).toInstant(),//24hs
                userRepository.findUserByEmail(email).get());
        return refreshTokenRepository.save(refreshToken);//- saving the refresh token to a database
    }

    public Optional<RefreshToken> findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findRefreshToken(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo( Instant.now()) < 0 ) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + "\nRefresh token was expired. Please make a new signin request");
        }
        return token;
    }
}

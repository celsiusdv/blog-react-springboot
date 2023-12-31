package com.users.usersmanagement.entity;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "refresh_tokens")
@Entity(name = "RefreshToken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Integer refreshTokenId;

    @Column
    private String refreshToken;

    @Column
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id_junction", referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name ="user_id_fk" ))//TODO: RESET DATABASE TO UPDATE COLUMNS
    private User user;

    public RefreshToken(){}
    public RefreshToken(String refreshToken, Instant expiryDate, User user){
        this.refreshToken= refreshToken;
        this.expiryDate=expiryDate;
        this.user=user;
    }
    public RefreshToken(Integer refreshTokenId, String refreshToken, Instant expiryDate, User user){
        this.refreshTokenId=refreshTokenId;
        this.refreshToken= refreshToken;
        this.expiryDate=expiryDate;
        this.user=user;
    }

    public void setRefreshTokenId(Integer refreshTokenId){this.refreshTokenId= refreshTokenId;}
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setUser(User user){
        this.user=user;
    }

    public Integer getRefreshTokenId(){
        return this.refreshTokenId;
    }
    public String getRefreshToken(){
        return this.refreshToken;
    }
    public Instant getExpiryDate(){
        return this.expiryDate;
    }
    public User getUser(){
        return this.user;
    }
    @Override
    public String toString(){
        return "refresh token: "+refreshToken+
                "\nrefresh token id: "+refreshTokenId+
                "\nexpiry date:"+expiryDate+
                "\nuser: "+this.user;
    }

}

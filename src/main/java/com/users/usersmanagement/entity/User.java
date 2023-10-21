package com.users.usersmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name="email_constraint", columnNames = {"email"} )
})
@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    @Column
    private String name;

    @NotBlank @Column(nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank @Column(nullable = false)
    private String password;

    public User(){}
    public User(String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public Integer getUserId(){ return this.userId; }
    public String getName(){ return this.name; }
    public String getEmail(){ return this.email; }
    @JsonIgnore //hide password on request. use @JsonProperty in the instance field variable declaration
    public String getPassword(){ return this.password; }

    @Override
    public String toString() {
        return "name: "+name+
                    "\nemail: "+email+
                    "\npassword: "+password+
                    "user id: "+userId+"\n";
    }
}

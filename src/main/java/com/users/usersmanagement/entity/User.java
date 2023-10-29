package com.users.usersmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name="email_constraint", columnNames = {"email"} )
})
@Entity(name = "User")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Integer userId;

    @Column
    private String name;

    @NotBlank @Column(nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank @Column(nullable = false)
    private String password;

    //configuring CascadeType to delete row from table users_and_roles first, to avoid conflict with "users" table
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_and_roles",
            joinColumns = @JoinColumn(name = "user_id_junction", referencedColumnName = "user_id",
                    foreignKey = @ForeignKey(name = "fk_user_id")),
            inverseJoinColumns = @JoinColumn(name = "role_id_junction", referencedColumnName = "role_id",
                    foreignKey = @ForeignKey(name = "fk_role_id")))
    private Set<Role> authorities;

    @Transient//annotation to avoid variable declaration being mapped to a table
    private String token;//this variable will not be mapped to a column

    public User(){}
    public User(String email, String password, Set<Role> authorities){
        this.email=email;
        this.password=password;
        this.authorities=authorities;
    }
    public User(String name, String email, String password, Set<Role> authorities){
        this.name=name;
        this.email=email;
        this.password=password;
        this.authorities=authorities;
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
    public void setAuthorities(Set<Role> authorities){
        this.authorities=authorities;
    }
    public void setToken(String token){ this.token=token;}

    public Integer getUserId(){ return this.userId; }
    public String getName(){ return this.name; }
    public String getEmail() {return this.email;}

    @JsonIgnore //hide password on request. use @JsonProperty in the instance field variable declaration
    public String getPassword(){ return this.password; }
    @Override public Set<Role> getAuthorities(){ return this.authorities;}
    public String getToken(){ return this.token;}

    @Override public String getUsername(){ return this.getEmail(); }
    @Override @JsonIgnore public boolean isAccountNonExpired() {return true;}
    @Override @JsonIgnore public boolean isAccountNonLocked() {return true;}
    @Override @JsonIgnore public boolean isCredentialsNonExpired() {return true;}
    @Override @JsonIgnore public boolean isEnabled() {return true;}

    @Override
    public String toString() {
        return "name: "+name+
                    "\nemail: "+email+
                    "\npassword: "+password+"\n"+
                    "user id: "+userId+"\n"+
                    "authorities: "+authorities;
    }
}

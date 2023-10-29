package com.users.usersmanagement.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Entity(name="Role")
public class Role implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    //using only {CascadeType.MERGE, CascadeType.REFRESH} to avoid duplicate entries
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "roles_and_privileges",
            joinColumns = @JoinColumn(name = "role_id_junction", referencedColumnName = "role_id",
                    foreignKey = @ForeignKey(name = "fk_role_id_and_privileges")),
            inverseJoinColumns = @JoinColumn(name = "privilege_id_junction", referencedColumnName = "privilege_id",
                    foreignKey = @ForeignKey(name = "fk_privilege_id")))
    private Set<Privilege> privileges=new HashSet<>();

    public Role(){}

    public void setAuthority(String authority){ this.authority=authority; }
    public void setPrivileges(Set<Privilege> privileges){ this.privileges=privileges; }

    @Override public String getAuthority() {
        return authority;
    }
    public Set<Privilege> getPrivileges(){ return privileges; }

    @Override
    public String toString() {
        return this.authority+" {\n"+
                "\tprivileges: "+this.privileges+"\n}";
    }
}

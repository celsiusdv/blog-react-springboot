package com.users.usersmanagement.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Table(name = "roles")
@Entity(name="Role")
public class Role implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "roles_and_permissions",
            joinColumns = @JoinColumn(name = "role_id_junction_for_permissions", referencedColumnName = "role_id",
                    foreignKey = @ForeignKey(name = "fk_roles_role_id_for_permissions")),
            inverseJoinColumns = @JoinColumn(name = "permission_id_junction", referencedColumnName = "permission_id",
                    foreignKey = @ForeignKey(name = "fk_permissions_permission_id")))
    private Set<Permission> privileges;

    public Role(){}

    public void setAuthority(String authority){ this.authority=authority; }
    public void setPrivileges(Set<Permission> privileges){ this.privileges=privileges; }
    @Override
    public String getAuthority() {
        return authority;
    }
    public Set<Permission> getPrivileges(){ return privileges; }
}

package com.spring.jwt.main.user;

import com.spring.jwt.main.role.Role;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Houston(Nayana)
 **/

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "created_by",
            updatable = false
    )
    private Integer createdBy = 0;
    @Column(
            name = "updated_by"
    )

    private Integer updatedBy = 0;
    @Column(
            name = "is_active"
    )
    private Boolean isActive = true;

    private String name;

    @Column(name = "email", length = 100,unique = true)
    @NotNull(message = "Email is required")
    @Size(min = 1,max = 100,message = "Email should be valid")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @Column(name = "google_auth_token")
    private String googleAuthToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}
    )
    private List<Role> roles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleAuthToken() {
        return googleAuthToken;
    }

    public void setGoogleAuthToken(String googleAuthToken) {
        this.googleAuthToken = googleAuthToken;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}

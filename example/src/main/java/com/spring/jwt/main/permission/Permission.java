package com.spring.jwt.main.permission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Houston(Nayana)
 **/

@Entity
@Table(name = "permission")
public class Permission{

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

    @Size(max = 100)
    @NotNull
    private String name;

    @Column(name = "display_name")
    @NotNull
    private String displayName;

    @Column(name = "description")
    private String description;

    public Permission() {
    }

    public Permission(@Size(max = 100) @NotNull String name, @NotNull String displayName, String description) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

package com.example.cookbook.bean;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleBean {
    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "description", nullable = true)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

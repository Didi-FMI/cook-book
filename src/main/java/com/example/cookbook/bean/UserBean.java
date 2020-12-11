package com.example.cookbook.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"favourites"})
public class UserBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true, length = 40)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 256)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "favourites",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "recipe")
    )
    private Set<RecipeBean> favourites = new TreeSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleBean> roles;

    public UserBean() {
    }

    public UserBean(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserBean(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RecipeBean> getFavourites() {
        return favourites;
    }

    public void setFavourites(TreeSet<RecipeBean> favourites) {
        this.favourites = favourites;
    }

    public Set<RoleBean> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBean> roles) {
        this.roles = roles;
    }
}

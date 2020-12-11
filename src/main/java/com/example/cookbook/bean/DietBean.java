package com.example.cookbook.bean;

import javax.persistence.*;

@Entity
@Table(name = "diet")
public class DietBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true )
    private String name;

    public DietBean() {
    }

    public DietBean(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

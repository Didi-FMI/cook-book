package com.example.cookbook.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recipe")
public class RecipeBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "image", nullable = false, length = 512)
    private String image;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserBean user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuisine_id")
    private CuisineBean cuisine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diet_id")
    private DietBean diet;

    public RecipeBean() {
    }

    public RecipeBean(
            String name,
            String description,
            String image,
            String ingredients,
            String instructions,
            UserBean user,
            CuisineBean cuisine,
            DietBean diet
    ) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.user = user;
        this.cuisine = cuisine;
        this.diet = diet;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RecipeBean)) return false;

        RecipeBean that = (RecipeBean) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

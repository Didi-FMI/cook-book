package com.example.cookbook.bean;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class CommentBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false, length = 2048)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserBean user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private RecipeBean recipe;

    public CommentBean() {

    }

    public CommentBean(String content, UserBean user, RecipeBean recipe) {
        this.content = content;
        this.user = user;
        this.recipe = recipe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public RecipeBean getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeBean recipe) {
        this.recipe = recipe;
    }
}

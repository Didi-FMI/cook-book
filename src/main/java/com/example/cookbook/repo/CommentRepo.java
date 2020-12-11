package com.example.cookbook.repo;

import com.example.cookbook.bean.CommentBean;
import com.example.cookbook.bean.RecipeBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<CommentBean, Integer> {
    List<CommentBean> findByRecipe(RecipeBean recipe);

    CommentBean findById(long id);
}

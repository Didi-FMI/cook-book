package com.example.cookbook.controller;

import com.example.cookbook.bean.CommentBean;
import com.example.cookbook.bean.RecipeBean;
import com.example.cookbook.bean.UserBean;
import com.example.cookbook.repo.CommentRepo;
import com.example.cookbook.repo.RecipeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentsController {
    CommentRepo commentRepo;

    RecipeRepo recipeRepo;

    public CommentsController(CommentRepo commentRepo, RecipeRepo recipeRepo) {
        this.commentRepo = commentRepo;
        this.recipeRepo = recipeRepo;
    }

    @GetMapping(path = "/comments/all")
    public List<CommentBean> isRecipeInFavourites(@RequestParam long recipeId) {

        RecipeBean recipe = recipeRepo.findById(recipeId);

        return commentRepo.findByRecipe(recipe);
    }

    @PostMapping(path = "/comments/post")
    public ResponseEntity<CommentBean> postComment(
            @RequestParam long recipeId,
            @RequestParam String content,
            HttpSession session) {

        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(recipeId);

            CommentBean comment = new CommentBean(content, user, recipe);

            comment = commentRepo.saveAndFlush(comment);

            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/comments/delete")
    public Boolean canDeleteComment(@RequestParam long id, HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        CommentBean comment = commentRepo.findById(id);

        return user.getId() == comment.getUser().getId();
    }

    @PostMapping(path = "/comments/delete")
    public ResponseEntity<Boolean> deleteComment(@RequestParam long id, HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {

            CommentBean comment = commentRepo.findById(id);

            commentRepo.delete(comment);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

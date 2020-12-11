package com.example.cookbook.controller;

import com.example.cookbook.bean.RecipeBean;
import com.example.cookbook.bean.UserBean;
import com.example.cookbook.repo.RecipeRepo;
import com.example.cookbook.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Set;

@RestController
public class FavouritesController {
    private UserRepo userRepo;

    private RecipeRepo recipeRepo;

    public FavouritesController(UserRepo userRepo, RecipeRepo recipeRepo) {
        this.userRepo = userRepo;
        this.recipeRepo = recipeRepo;
    }

    @GetMapping(path = "/favourites/check")
    public Boolean isRecipeInFavourites(
            @RequestParam long id,
            HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(id);

            return user.getFavourites().contains(recipe);
        } else {
            return false;
        }
    }

    @PostMapping(path = "/favourites/add")
    public ResponseEntity<Boolean> addToFavourite(
            @RequestParam long id,
            HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(id);
            if (recipe != null) {

                user.getFavourites().add(recipe);

                userRepo.save(user);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/favourites/remove")
    public ResponseEntity<Boolean> removeFromFavourite(
            @RequestParam long id,
            HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(id);
            if (recipe != null) {

                user.getFavourites().remove(recipe);

                userRepo.save(user);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/favourites/all")
    public Set<RecipeBean> getAllFavourites(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            return user.getFavourites();
        } else {
            return null;
        }
    }
}

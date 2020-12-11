package com.example.cookbook.controller;

import com.example.cookbook.bean.CuisineBean;
import com.example.cookbook.bean.DietBean;
import com.example.cookbook.bean.RecipeBean;
import com.example.cookbook.bean.UserBean;
import com.example.cookbook.repo.CuisineRepo;
import com.example.cookbook.repo.DietRepo;
import com.example.cookbook.repo.RecipeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RecipeController {
    private RecipeRepo recipeRepo;

    private CuisineRepo cuisineRepo;

    private DietRepo dietRepo;

    public RecipeController(
            RecipeRepo recipeRepo,
            CuisineRepo cuisineRepo,
            DietRepo dietRepo
    ) {
        this.recipeRepo = recipeRepo;
        this.cuisineRepo = cuisineRepo;
        this.dietRepo = dietRepo;
    }

    @GetMapping(path = "/recipe/search")
    public List<RecipeBean> searchRecipes(
            @RequestParam String name,
            @RequestParam int cuisineId,
            @RequestParam int dietId
    ) {
        CuisineBean cuisine = cuisineRepo.findById(cuisineId);

        DietBean diet = dietRepo.findById(dietId);

        if (cuisineId == 1 && dietId == 1) {
            return recipeRepo.findAllByNameIgnoreCase(name);
        } else if (dietId == 1) {
            return recipeRepo.findAllByNameIgnoreCaseAndCuisine(name, cuisine);
        } else if (cuisineId == 1) {
            return recipeRepo.findAllByNameIgnoreCaseAndDiet(name, diet);
        }

        return recipeRepo.findAllByNameIgnoreCaseAndCuisineAndDiet(name, cuisine, diet);
    }

    @GetMapping(path = "/recipe/user")
    public ResponseEntity<List<RecipeBean>> recipesForUser(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            List<RecipeBean> result = recipeRepo.findByUser(user);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/recipe/create")
    public ResponseEntity<RecipeBean> createRecipe(
            @RequestParam String name,
            @RequestParam String image,
            @RequestParam String description,
            @RequestParam String ingredients,
            @RequestParam String instructions,
            @RequestParam int cuisineId,
            @RequestParam int dietId,
            HttpSession session
    ) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {

            CuisineBean cuisine = cuisineRepo.findById(cuisineId);

            DietBean diet = dietRepo.findById(dietId);

            RecipeBean recipeBean = new RecipeBean(
                    name,
                    description,
                    image,
                    ingredients,
                    instructions,
                    user,
                    cuisine,
                    diet
            );

            recipeBean = recipeRepo.saveAndFlush(recipeBean);

            if (recipeBean != null) {
                return new ResponseEntity<>(recipeBean, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(path = "/recipe/delete")
    public ResponseEntity<Long> deleteRecipe(
            @RequestParam long id,
            HttpSession session
    ) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(id);
            if (recipe != null) {
                recipeRepo.delete(recipe);

                return new ResponseEntity<>(id, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/recipe/delete")
    public boolean canDeleteRecipe(
            @RequestParam long id,
            HttpSession session
    ) {
        UserBean user = (UserBean) session.getAttribute("user");

        if (user != null) {
            RecipeBean recipe = recipeRepo.findById(id);

            return user.getId() == recipe.getUser().getId();
        } else {
            return false;
        }
    }

    @GetMapping(path = "/recipe/get")
    public RecipeBean getRecipe(@RequestParam long id) {
        return recipeRepo.findById(id);
    }

    @GetMapping(path = "/recipe/cuisines")
    public List<CuisineBean> getCuisines() {
        return cuisineRepo.findAll();
    }

    @GetMapping(path = "/recipe/diets")
    public List<DietBean> getDiets() {
        return dietRepo.findAll();
    }
}

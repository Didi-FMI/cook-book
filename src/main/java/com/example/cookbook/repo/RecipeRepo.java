package com.example.cookbook.repo;

import com.example.cookbook.bean.CuisineBean;
import com.example.cookbook.bean.DietBean;
import com.example.cookbook.bean.RecipeBean;
import com.example.cookbook.bean.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepo extends JpaRepository<RecipeBean, Integer> {

    List<RecipeBean> findByUser(UserBean user);

    List<RecipeBean> findAllByNameContainingIgnoreCase(String name);

    List<RecipeBean> findAllByNameContainingIgnoreCaseAndCuisine(String name, CuisineBean cuisine);

    List<RecipeBean> findAllByNameContainingIgnoreCaseAndDiet(String name, DietBean diet);

    List<RecipeBean> findAllByNameContainingIgnoreCaseAndCuisineAndDiet(String name, CuisineBean cuisine, DietBean diet);

    RecipeBean findById(long id);
}

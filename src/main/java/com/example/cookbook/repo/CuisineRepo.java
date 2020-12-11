package com.example.cookbook.repo;

import com.example.cookbook.bean.CuisineBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepo extends JpaRepository<CuisineBean, Integer> {
    CuisineBean findById(long id);

    CuisineBean findByName(String name);
}

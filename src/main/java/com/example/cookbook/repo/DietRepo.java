package com.example.cookbook.repo;

import com.example.cookbook.bean.DietBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepo extends JpaRepository<DietBean, Integer> {
    DietBean findById(long id);

    DietBean findByName(String name);
}

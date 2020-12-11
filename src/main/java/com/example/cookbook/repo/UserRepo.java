package com.example.cookbook.repo;

import com.example.cookbook.bean.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer>{

	UserBean findByUsernameIgnoreCaseAndPassword(String username, String password);
	
	UserBean findByUsernameIgnoreCase(String username);

	UserBean findByEmailIgnoreCase(String email);
}

package com.example.cookbook.controller;

import com.example.cookbook.WebSecurityConfig;
import com.example.cookbook.bean.UserBean;
import com.example.cookbook.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class LoginController {
    private UserRepo userRepo;

    private WebSecurityConfig webSecurityConfig;

    public LoginController(UserRepo userRepo, WebSecurityConfig webSecurityConfig) {
        this.userRepo = userRepo;
        this.webSecurityConfig = webSecurityConfig;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> register(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "repeatPassword") String repeatPassword,
            HttpSession session
    ) {
        if (password.equals(repeatPassword)) {

            if (userRepo.findByUsernameIgnoreCase(username) != null || userRepo.findByEmailIgnoreCase(email) != null) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }

            UserBean user = new UserBean(username, hashPassword(password), email);

            UserBean newUser = userRepo.saveAndFlush(user);

            session.setAttribute("user", newUser);

            return new ResponseEntity<>(true, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpSession session
    ) {
        UserBean user = userRepo.findByUsernameIgnoreCaseAndPassword(username, hashPassword(password));

        if (user != null) {
            session.setAttribute("user", user);

            try {
                UserDetails userDetails = webSecurityConfig.userDetailsServiceBean().loadUserByUsername(user.getUsername());

                if (userDetails != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);

                    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

                    HttpSession http = attr.getRequest().getSession(true);
                    http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                }

                return new ResponseEntity<>(true, HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    private String hashPassword(String password) {

        StringBuilder result = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            for (byte aByte : bytes) {
                result.append((char) aByte);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}

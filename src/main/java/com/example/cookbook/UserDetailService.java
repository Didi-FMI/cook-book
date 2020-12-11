package com.example.cookbook;

import com.example.cookbook.bean.RoleBean;
import com.example.cookbook.bean.UserBean;
import com.example.cookbook.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private UserRepo userRepo;

    public UserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserBean user = userRepo.findByUsernameIgnoreCase(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        Set<RoleBean> roles = user.getRoles();

        return new UserPrincipal(user, roles);
    }
}

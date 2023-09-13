package com.example.sudoswap.service.common;

import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    private final UserRepository userRepository;

    public CommonService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getLoggedInUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username).orElseThrow(Exception::new);
            }
        }
        return null;
    }

    public UserEntity getAdminUser() throws Exception {
        return userRepository.findByUsername("admin").orElseThrow(Exception::new);
    }
}

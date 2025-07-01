package com.coworking.management_user.configuration.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("testuser".equals(username)) {
            return User.builder()
                    .username("testuser")
                    .password("{noop}password")
                    .roles("USER")
                    .build();
        }//  <- Eliminar esto :v
        throw new UsernameNotFoundException("Not required for this service");
    }
}

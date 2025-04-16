package com.coworking.spaces_service.configuration.security;

import com.coworking.spaces_service.configuration.jwt.JwtAuthenticationFilter;
import com.coworking.spaces_service.util.enums.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring SecurityFilterChain...");

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    logger.debug("Configuring authorized requests...");
                    authorize
                            //PROXIMA IMPLEMENTATION
                            //.requestMatchers("api/v1/management").hasAnyRole(RoleName.ADMIN.name(), RoleName.USER.name())
                            .requestMatchers("api/v1/spaces/**").permitAll()//hasAuthority(RoleName.USER.name())
                            .requestMatchers("api/v1/test/user").hasAnyRole(RoleName.USER.name())
                            .requestMatchers("api/v1/test/admin").hasAnyRole(RoleName.ADMIN.name())
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    logger.debug("Configuring session management...");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Creating BCryptPasswordEncoder bean...");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.debug("Creating AuthenticationManager bean...");
        return authenticationConfiguration.getAuthenticationManager();
    }

}

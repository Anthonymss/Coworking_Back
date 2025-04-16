package com.coworking.management_user.service.impl;

import com.coworking.management_user.exception.EmailMismatchException;
import com.coworking.management_user.exception.EmailNotFoundException;
import com.coworking.management_user.persistence.entity.User;
import com.coworking.management_user.persistence.entity.UserAuthentication;
import com.coworking.management_user.persistence.repository.UserAuthenticationRepository;
import com.coworking.management_user.persistence.repository.UserRepository;
import com.coworking.management_user.presentation.dto.UserDto;
import com.coworking.management_user.service.feignclient.AuthServiceFeignClient;
import com.coworking.management_user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;
    private static final String AUTH_SERVICE_NAME = "auth-service";
    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public String updateUser( UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new EmailNotFoundException("Not found user with email " + userDto.getEmail());
        }
        try {
            User user = userOptional.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setProfileImageUrl(userDto.getProfileImageUrl());
            userRepository.save(user);
            return "User updated successfully";
        }catch (Exception e){
            return "Failed to update user";
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new EmailNotFoundException("Not found user with id " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> userOpt=userRepository.findByEmail(email);
        if (userOpt.isEmpty()){
            throw new EmailNotFoundException("Not found user with email " + email);
        }
        User user=userOpt.get();
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .accountCreated(user.getAccountCreated())
                .profileImageUrl(user.getProfileImageUrl())
                .statusOauthEnabled(user.getStatusOauthEnabled())
                .build();
    }
    @Override
    public String synchronizeAccountGoogle(String email, String token) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new EmailNotFoundException("Not found user with email " + email);
        }
        User user = userOpt.get();
        UserAuthentication userAuthentication = new UserAuthentication();
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        Map<String, String> mapResponseInfoGoogle = authServiceFeignClient.getGoogleAccountInfo(body);
        String emailResponse=mapResponseInfoGoogle.get("email");
        if (!email.equals(emailResponse))
            throw new EmailMismatchException("Emails do not match");
        user.setProfileImageUrl(mapResponseInfoGoogle.get("profileImageUrl"));
        user.setStatusOauthEnabled(true);
        userAuthentication.setAuthProvider("Google");
        userAuthentication.setProviderId(mapResponseInfoGoogle.get("id"));
        userAuthentication.setUser(user);
        userAuthenticationRepository.save(userAuthentication);
        userRepository.save(user);
        return "Se sincronizo correctamente :)";
    }



}

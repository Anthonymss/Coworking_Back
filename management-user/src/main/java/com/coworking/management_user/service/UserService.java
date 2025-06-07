package com.coworking.management_user.service;

import com.coworking.management_user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    String updateUser(UserDto userDto, MultipartFile file);
    void deleteUser(Long id);
    UserDto getUserByEmail(String email);

    String synchronizeAccountGoogle(String email, String token);
}

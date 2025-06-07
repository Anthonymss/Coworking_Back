package com.coworking.management_user.controller;

import com.coworking.management_user.dto.UserDto;
import com.coworking.management_user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/v1/management/user")
@AllArgsConstructor
public class ManagementUserController {
    private final UserService userService;
    @GetMapping("{email}")
    public ResponseEntity<UserDto> getInfoUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("synchronize-google")
    public ResponseEntity<String> synchronizeAccountGoogle(@RequestBody Map<String, String> body) {
        String result = userService.synchronizeAccountGoogle(body.get("email"), body.get("token"));
        return ResponseEntity.ok(result);
    }
    /*
    @PutMapping("update-user")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto, file), HttpStatus.NO_CONTENT);
    }
    */
    @PutMapping(value = "update-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUser(
            @RequestPart("user") UserDto userDto,
            @RequestPart("file") MultipartFile file
    ) {
        userService.updateUser(userDto, file);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

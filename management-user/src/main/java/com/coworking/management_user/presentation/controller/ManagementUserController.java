package com.coworking.management_user.presentation.controller;

import com.coworking.management_user.presentation.dto.UserDto;
import com.coworking.management_user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("SE ELIMINO??");
        return ResponseEntity.noContent().build();
    }
    @PostMapping("synchronize-google")
    public ResponseEntity<String> synchronizeAccountGoogle(@RequestBody Map<String, String> body) {
        String result = userService.synchronizeAccountGoogle(body.get("email"), body.get("token"));
        return ResponseEntity.ok(result);
    }
    @PutMapping("update-user")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.NO_CONTENT);
    }
}

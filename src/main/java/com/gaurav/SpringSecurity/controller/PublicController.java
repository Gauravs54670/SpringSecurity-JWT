package com.gaurav.SpringSecurity.controller;

import com.gaurav.SpringSecurity.model.SignUp;
import com.gaurav.SpringSecurity.model.UserDTO;
import com.gaurav.SpringSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private UserService userService;
    public PublicController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("register-user")
    public ResponseEntity<?> registerUser(@RequestBody SignUp signUp) {
        try {
            UserDTO userDTO = this.userService.registerUser(signUp);
            return new ResponseEntity<>(Map.of(
                    "message", "User registered successfully",
                    "response", userDTO
            ), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(Map.of(
                    "message", "Error in registering user",
                    "response", exception.getMessage()
            ),HttpStatus.BAD_REQUEST);
        }
    }
}

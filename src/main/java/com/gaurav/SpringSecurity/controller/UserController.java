package com.gaurav.SpringSecurity.controller;

import com.gaurav.SpringSecurity.model.UserDTO;
import com.gaurav.SpringSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/get-details")
    public ResponseEntity<?> getDetails() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDTO userDTO = this.userService.getDetails(email);
            return new ResponseEntity<>(Map.of(
                    "message", "details get successfully",
                    "response", userDTO
            ),HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(Map.of(
                    "message", "error in getting details",
                    "response", exception.getMessage()
            ), HttpStatus.BAD_REQUEST);
        }
    }
}

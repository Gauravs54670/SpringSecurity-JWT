package com.gaurav.SpringSecurity.controller;

import com.gaurav.SpringSecurity.jwt.JwtUtils;
import com.gaurav.SpringSecurity.model.SignIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController @RequestMapping("/auth")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public JwtAuthenticationController(
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/login")
    public ResponseEntity<?> generateJwtToken(@RequestBody SignIn signIn) {
        try {
            //authenticate the user by email and password
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signIn.getEmail(), signIn.getPassword()));
            log.info("Authentication is completed and Authentication object contains the details of user (UserDetailsService)");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = this.jwtUtils.generateJwtToken(userDetails);
            return new ResponseEntity<>(Map.of(
                    "message","Token generation successful.",
                    "response", jwtToken
            ), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(Map.of(
                    "message", "authentication failed",
                    "response", exception.getMessage()
            ),HttpStatus.UNAUTHORIZED);
        }
    }
}

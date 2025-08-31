package com.gaurav.SpringSecurity.service;

import com.gaurav.SpringSecurity.model.SignUp;
import com.gaurav.SpringSecurity.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDTO registerUser(SignUp signUp);
    public UserDTO getDetails(String email);
}

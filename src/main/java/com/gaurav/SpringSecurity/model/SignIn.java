package com.gaurav.SpringSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
public class SignIn {
    private String email;
    private String password;
}

package com.gaurav.SpringSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
public class SignUp {
    private String email;
    private String contact;
    private String fullName;
    private String password;
    private String mediaUrl;
}

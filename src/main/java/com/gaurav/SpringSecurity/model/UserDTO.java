package com.gaurav.SpringSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
public class UserDTO {
    private String email;
    private String contact;
    private String fullName;
    private String mediaUrl;
}

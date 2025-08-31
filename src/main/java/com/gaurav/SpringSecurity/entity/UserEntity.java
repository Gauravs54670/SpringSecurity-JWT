package com.gaurav.SpringSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
@Document(collection = "form_application")
public class UserEntity {
    @Id
    private String userId;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String contact;
    private String fullName;
    private String password;
    private String mediaUrl;
    private LocalDateTime accountCreationTime;
    private LocalDateTime accountUpdateTime;
    private LocalDateTime lastLogin;
    private Set<UserRole> userRoles;
}

package com.gaurav.SpringSecurity.service;

import com.gaurav.SpringSecurity.entity.UserEntity;
import com.gaurav.SpringSecurity.entity.UserRole;
import com.gaurav.SpringSecurity.model.SignUp;
import com.gaurav.SpringSecurity.model.UserDTO;
import com.gaurav.SpringSecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDTO registerUser(SignUp signUp) {
        boolean exist = isPresent(signUp.getEmail(), signUp.getContact());
        if(exist)
            throw new RuntimeException("Credentials are already registered.");
        UserEntity user = UserEntity.builder()
                .email(signUp.getEmail())
                .contact("+91 " + signUp.getContact())
                .fullName(signUp.getFullName())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .mediaUrl(signUp.getMediaUrl())
                .accountCreationTime(LocalDateTime.now())
                .accountUpdateTime(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .userRoles(Set.of(UserRole.USER))
                .build();
        user = this.userRepository.save(user);
        return convertToDTO(user);
    }
    @Override
    public UserDTO getDetails(String email) {
        UserEntity user = this.userRepository.findByEmail(email).orElseThrow();
        return convertToDTO(user);
    }
    //helper method
    private boolean isPresent(String email, String contact) {
        boolean exist1 = this.userRepository.findByEmail(email).isPresent();
        boolean exist2 = this.userRepository.findByContact(contact).isPresent();
        return exist1 || exist2;
    }
    private UserDTO convertToDTO(UserEntity user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .contact(user.getContact())
                .fullName(user.getFullName())
                .mediaUrl(user.getMediaUrl())
                .build();
    }
}

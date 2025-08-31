package com.gaurav.SpringSecurity.repository;

import com.gaurav.SpringSecurity.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findByContact(String contact);
}

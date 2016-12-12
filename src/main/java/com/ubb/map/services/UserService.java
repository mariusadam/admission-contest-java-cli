package com.ubb.map.services;

import com.ubb.map.domain.User;
import com.ubb.map.repository.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Service class handling crud operations on User entity
 */
@Singleton
public class UserService {
    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String encodePassword(String plain, String salt) {

        return null;
    }

    public User getUserByEmailAndPassword(String email, String plainPassword) throws SQLException {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        return this.userRepository
                .findByEmailAndPassword(
                        email,
                        encodePassword(plainPassword, user.getSalt())
                );
    }
}

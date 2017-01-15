package com.ubb.map.services;

import com.ubb.map.domain.User;
import com.ubb.map.repository.db.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Random;

/**
 * Service class handling crud operations on User entity
 */
@Singleton
public class UserService {
    private final static int SALT_SIZE = 32;
    private UserRepository userRepository;
    private Random random;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.random = new SecureRandom();
    }

    public String encodePassword(String plainPassword, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(plainPassword.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return generatedPassword;
    }

    public String getSalt() {
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return new String(salt);
    }

    public User getUserByEmailAndPassword(String email, String plainPassword) throws SQLException {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        user.eraseCredentials();
        String hashedPass = encodePassword(plainPassword, user.getSalt());
        return hashedPass.equals(user.getPassword()) ?
                user : null;
    }
}

package com.ubb.map.services.crud;

import com.ubb.map.domain.User;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.UserRepository;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.validator.UserValidator;
import com.ubb.map.validator.ValidatorInterface;

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
public class UserService extends BaseCrudService<Integer, User>{
    private final static int SALT_SIZE = 32;
    private UserRepository repository;
    private Random random;

    @Inject
    public UserService(UserRepository repository, UserValidator validator) {
        super(validator);
        this.repository = repository;
        random = new SecureRandom();
    }

    public User update(User u) throws InvalidObjectException, DuplicateEntryException, SQLException {
        validator.validate(u);
        repository.update(u);
        return u;
    }

    public String encodePassword(String plainPassword, String salt){
        String generatedPassword;
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
        User user = this.repository.findByEmail(email);
        if (user == null) {
            return null;
        }
        user.eraseCredentials();
        String hashedPass = encodePassword(plainPassword, user.getSalt());
        return hashedPass.equals(user.getPassword()) ?
                user : null;
    }

    @Override
    protected RepositoryInterface<Integer, User> getRepository() {
        return repository;
    }
}

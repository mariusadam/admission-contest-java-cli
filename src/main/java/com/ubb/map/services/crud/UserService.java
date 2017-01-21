package com.ubb.map.services.crud;

import com.ubb.map.domain.Role;
import com.ubb.map.domain.User;
import com.ubb.map.domain.UserRole;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.UserRepository;
import com.ubb.map.repository.db.UserRoleRepository;
import com.ubb.map.validator.UserValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Service class handling crud operations on User entity
 */
@Singleton
public class UserService extends BaseCrudService<Integer, User> {
    private final static int SALT_SIZE = 32;
    private UserRepository repository;
    private Random random;
    private UserRoleRepository userRoleRepository;

    @Inject
    public UserService(UserRepository repository,UserValidator validator, UserRoleRepository ur) {
        super(validator);
        this.repository = repository;
        userRoleRepository = ur;
        random = new SecureRandom();
    }

    public User create(String email, String pass, String fn, String ln, Boolean isActive, Collection<Role> roles) throws Exception {
        User u = repository.findByEmail(email);
        if (u != null) {
            throw new DuplicateEntryException("This email is already taken");
        }

        u = new User();
        u.setPlainPassword(pass);
        u.setEmail(email);
        u.setFirstName(fn);
        u.setLastName(ln);
        u.setIsActive(isActive);
        u.setLoggedIn(false);
        u.setSalt(getSalt());
        u.setPassword(encodePassword(u.getPlainPassword(), u.getSalt()));

        validator.validate(u);
        repository.insert(u);
        u = repository.findByEmail(u.getEmail());
        for(Role r : roles) {
            userRoleRepository.insert(new UserRole(u, r));
        }

        return u;
    }

    public User update(String id, String email, String fn, String ln, Boolean isActive, Collection<Role> roles) throws Exception{
        User u = repository.findById(getIntOrNull(id));
        u.setEmail(email);
        u.setFirstName(fn);
        u.setLastName(ln);
        u.setIsActive(isActive);
        u.setLoggedIn(false);

        validator.validate(u);
        repository.update(u);
        userRoleRepository.updateUserRoles(u, roles);
        return u;
    }

    public User update(String id, String email, String fn, String ln, Boolean isActive) throws Exception{
        User u = repository.findById(getIntOrNull(id));
        u.setEmail(email);
        u.setFirstName(fn);
        u.setLastName(ln);
        u.setIsActive(isActive);
        u.setLoggedIn(false);

        validator.validate(u);
        repository.update(u);
        return u;
    }

    public User update(User u) throws Exception {
        validator.validate(u);
        repository.update(u);
        return u;
    }

    public String encodePassword(String plainPassword, String salt) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return generatedPassword;
    }

    public User updatePassword(User u) throws InvalidObjectException, SQLException {
        validator.validate(u);
        u.setPassword(encodePassword(u.getPlainPassword(), u.getSalt()));
        repository.update(u);
        u.eraseCredentials();

        return u;
    }

    public String getSalt() {
        return UUID.randomUUID().toString();
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

    public List<Role> getRoles(User user) {
        return userRoleRepository.getRoles(user);
    }

    @Override
    protected RepositoryInterface<Integer, User> getRepository() {
        return repository;
    }
}

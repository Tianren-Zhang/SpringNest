package com.laioffer.springnest.service;

import com.laioffer.springnest.exception.UserAlreadyExistException;
import com.laioffer.springnest.model.Authority;
import com.laioffer.springnest.model.User;
import com.laioffer.springnest.model.UserRole;
import com.laioffer.springnest.repository.AuthorityRepository;
import com.laioffer.springnest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Provide a service to register new users.
// It ensures passwords are encoded before storage and uses a transactional approach to guarantee that the operations are atomic
@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // All database operations inside the method will either completely succeed or completely fail, ensuring data integrity.
    @Transactional
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }
}
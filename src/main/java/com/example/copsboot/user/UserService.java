package com.example.copsboot.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findUserByAuthServerId(AuthServerId authServerId) {
        return repository.findByAuthServerId(authServerId);
    }

    public User createUser(CreateUserParameters parameters) {
        UserId userId = repository.nextId();
        User user = new User(
            userId,
            parameters.email(),
            parameters.authServerId(),
            parameters.mobileToken()
        );
        return repository.save(user);
    }

    public Optional<User> findUserById(UserId id) {
        return repository.findById(id);
    }

    public User getUserById(UserId id) {
        return findUserById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public Optional<User> findUserByMobileToken(String mobileToken) {
        return repository.findByMobileToken(mobileToken);
    }
}

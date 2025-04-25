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
}

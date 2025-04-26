package com.example.copsboot.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(AuthServerId id) {
        super("User not found with auth server ID: " + id.toString());
    }
}

package com.example.copsboot.user;

import com.example.orm.jpa.UniqueIdGenerator;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final UniqueIdGenerator<UserId> generator;

    public UserRepositoryImpl(UniqueIdGenerator<UserId> generator) {
        this.generator = generator;
    }
    
    @Override
    public UserId nextId() {
        return generator.getNextUniqueId();
    }    
}

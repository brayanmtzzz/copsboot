package com.example.copsboot.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UserId>, UserRepositoryCustom {
}
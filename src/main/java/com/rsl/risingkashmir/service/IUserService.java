package com.rsl.risingkashmir.service;

import com.rsl.risingkashmir.entiry.UserEntity;

import java.util.Optional;

public interface IUserService {
    Integer saveUser(UserEntity user);

    Optional<UserEntity> findByUsername(String username);

}

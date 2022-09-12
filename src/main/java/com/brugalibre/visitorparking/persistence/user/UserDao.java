package com.brugalibre.visitorparking.persistence.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends CrudRepository<UserEntity, UUID> {

   Optional<UserEntity> findByUsername(String username);
}

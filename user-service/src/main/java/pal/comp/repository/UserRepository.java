package pal.comp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pal.comp.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
}

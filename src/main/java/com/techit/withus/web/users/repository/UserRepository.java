package com.techit.withus.web.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techit.withus.web.users.domain.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{
    Optional<Users> findByUsername(String username);
    List<Users> findByUserIdIn(List<Long> userIds);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

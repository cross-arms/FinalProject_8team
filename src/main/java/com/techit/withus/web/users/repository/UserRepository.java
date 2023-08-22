package com.techit.withus.web.users.repository;

import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>
{
    Optional<Users> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
package com.techit.withus.redis.repository;

import com.techit.withus.redis.hashes.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>
{
}

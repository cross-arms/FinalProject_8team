package com.techit.withus.redis.repository;

import com.techit.withus.redis.hashes.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, String>
{
}

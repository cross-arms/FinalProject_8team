package com.techit.withus.redis.repository;

import com.techit.withus.redis.hashes.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String>
{
}

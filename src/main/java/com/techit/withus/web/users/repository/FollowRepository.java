package com.techit.withus.web.users.repository;

import com.techit.withus.web.users.domain.entity.Follows;
import com.techit.withus.web.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follows, Long>
{
    Long countAllByWhoFollow(Users users);

    Long countAllByFollowWho(Users users);
}

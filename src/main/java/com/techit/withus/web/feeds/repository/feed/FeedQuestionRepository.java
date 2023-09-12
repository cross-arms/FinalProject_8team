package com.techit.withus.web.feeds.repository.feed;

import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import com.techit.withus.web.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedQuestionRepository extends JpaRepository<FeedQuestion, Long>
{
    Long countAllByChosenPerson(Users users);
}

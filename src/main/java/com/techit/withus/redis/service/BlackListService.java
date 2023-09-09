package com.techit.withus.redis.service;

import com.techit.withus.redis.hashes.BlackList;
import com.techit.withus.redis.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListService
{
    private final BlackListRepository blackListRepository;

    public void addBlackList(String accessToken)
    {
        blackListRepository.save(new BlackList(accessToken));
    }
}

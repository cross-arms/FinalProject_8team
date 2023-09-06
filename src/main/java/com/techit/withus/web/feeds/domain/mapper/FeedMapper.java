package com.techit.withus.web.feeds.domain.mapper;

import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedMapper {
    FeedMapper INSTANCE = Mappers.getMapper(FeedMapper.class);

    // 작성자 ID 매핑
    @Mapping(source = "writer.userId", target = "writer")
    List<FeedDTO> toDto(List<Feeds> feeds);

    default Long map(Users user) {
        return user.getUserId();
    }
}

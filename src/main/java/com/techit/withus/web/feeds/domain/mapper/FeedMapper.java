package com.techit.withus.web.feeds.domain.mapper;

import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FeedMapper {
    FeedMapper INSTANCE = Mappers.getMapper(FeedMapper.class);

    @Mapping(source = "writer.username", target = "writer")
    FeedDTO toFeedDTO(Feeds feeds);

    List<FeedDTO> toFeedDTOList(List<Feeds> feedsList);
}

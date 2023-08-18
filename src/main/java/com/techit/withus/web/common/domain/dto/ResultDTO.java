package com.techit.withus.web.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDTO
{
    private Long status;
    private String message;
    private Object data;
}

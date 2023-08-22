package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO
{
    private String address;
    private String code;
}

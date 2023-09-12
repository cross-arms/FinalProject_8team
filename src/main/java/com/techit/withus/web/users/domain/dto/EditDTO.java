package com.techit.withus.web.users.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditDTO
{
    private String username;
    private String beforePassword;
    private String afterPassword;
    private String profileURL;
    private String oneLineIntroduction;
}

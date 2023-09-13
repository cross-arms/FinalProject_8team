package com.techit.withus.web.users.domain.entity;

import com.techit.withus.oauth.OAuth2Provider;
import com.techit.withus.web.users.domain.dto.UserDto;
import com.techit.withus.web.users.domain.dto.UserDto.UserResponse;
import com.techit.withus.web.users.domain.enumeration.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email; // 이메일 형식 (OAuth2 사용자와 통일)으로 로그인
    private String password;

    private String username;
    // 로그인할 때는 email로 로그인을 하고, 실제 서비스에서 노출되는 아이디는 username(nickname의 개념)
    private String phone;

    private Long point;
    private String profileURL;
    private String personalURL;
    private String oneLineIntroduction;
    private String detailedIntroduction;

    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;
    @Enumerated(EnumType.STRING)
    private Roles role;

    public static Users fromDto(UserResponse userResponse) {
        return Users.builder()
                .userId(userResponse.getUserId())
                .username(userResponse.getUsername())
                .profileURL(userResponse.getProfileURL())
                .oneLineIntroduction(userResponse.getOneLineIntroduction())
                .build();
    }
}

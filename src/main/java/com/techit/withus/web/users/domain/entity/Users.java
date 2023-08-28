package com.techit.withus.web.users.domain.entity;

import com.techit.withus.oauth.OAuth2Provider;
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
    /**
     * deprecated. email 형식의 아이디를 사용하므로 username 컬럼을 삭제할 예정임.
     * username을 참조하는 곳에서 문제가 발생하기 때문에 놔둠.
      */

    private String nickname;
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
}

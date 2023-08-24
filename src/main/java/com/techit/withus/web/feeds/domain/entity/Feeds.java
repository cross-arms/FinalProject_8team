package com.techit.withus.web.feeds.domain.entity;

import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feeds")
public class Feeds
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users writer;

    // 피드 종류: ex) 일반 / 질문
    private String type;
    // 공개 범위: ex) 비공개 / 전체 공개 / 팔로워에게만 공개
    private String scope;
    private String title;
    private String content;

    @Column(name = "created_date")
    private Timestamp createdDate;
}

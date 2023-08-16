package com.techit.withus.web.feeds.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Questions
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feeds feeds;
    // 질문 피드의 예치금: ex) 3000, 5000
    private Long deposit;
    // 질문 피드의 해결 상태: ex) 해결중, 해결완료
    private String status;
}

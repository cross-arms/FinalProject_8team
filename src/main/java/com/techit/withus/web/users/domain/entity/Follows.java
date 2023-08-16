package com.techit.withus.web.users.domain.entity;

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
@Table(name = "follows")
public class Follows
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // 누가 팔로우 했는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "who_follow")
    private Users whoFollow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_who")
    private Users followWho;
}

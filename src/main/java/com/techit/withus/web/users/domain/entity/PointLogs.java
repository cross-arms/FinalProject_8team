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
@Table(name = "point_logs")
public class PointLogs
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    // 포인트 사용 종류: ex) 충전 / 소모 / 출금
    private String type;
    // 발생 금액: ex) +5000, -5000
    private Long amount;
    // 잔여 금액: ex) 10000, 25000
    private Long remains;
}

package com.techit.withus.web.users.controller;

import com.techit.withus.web.users.domain.dto.StatisticsDTO;
import com.techit.withus.web.users.domain.dto.UserInfoDTO;
import com.techit.withus.web.users.service.StatisticsService;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StatisticsService statisticsService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(
            @PathVariable Long userId)
    {
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoDTO);
    }

    @GetMapping("/statistics/{userId}")
    public ResponseEntity<StatisticsDTO> getUserStatistics(
            @PathVariable Long userId)
    {
        StatisticsDTO statisticsDTO = statisticsService.getUserStatistics(userId);
        return ResponseEntity.ok(statisticsDTO);
    }
}

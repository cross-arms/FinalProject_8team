package com.techit.withus.common.fixture.users;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;

public class UserFixture {

    public static Users createUsers(){
        return Users.builder()
            .email("test@test.com")
            .createdDate(Timestamp.from(Instant.now()))
            .phone("000-0000-000")
            .modifiedDate(Timestamp.from(Instant.now()))
            .password("12341234")
            .personalURL("www.github.com")
            .point(1234L)
            .role(Roles.ROLE_USER)
            .username("테스트 유저")
            .build();
    }

    public static Users createUsersWithId(Long id){
        Users users = createUsers();
        ReflectionTestUtils.setField(users, "userId", id);
        return users;
    }

    public static List<Users> createUsersList(int size){
        List<Users> usersList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            usersList.add(createUsersWithId((long)i));
        }
        return usersList;
    }
}

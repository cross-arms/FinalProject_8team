INSERT INTO users (username, password, email, phone, point, profileURL, personalURL, one_line_introduction, detailed_introduction, created_date, modified_date, role)
VALUES
    ('user1', 'password1', 'user1@example.com', '123-456-7890', 100, 'profile1.jpg', 'https://user1.website.com', '안녕하세요!', '자세한 소개 내용 1', NOW(), NOW(), 'ROLE_USER'),
    ('user2', 'password2', 'user2@example.com', '987-654-3210', 200, 'profile2.jpg', 'https://user2.website.com', '반갑습니다!', '자세한 소개 내용 2', NOW(), NOW(), 'ROLE_USER'),
    ('admin', 'adminpass', 'admin@example.com', '555-555-5555', 500, 'admin.jpg', 'https://admin.website.com', '관리자입니다.', '자세한 소개 내용 3', NOW(), NOW(), 'ROLE_ADMIN'),
    ('user3', 'password3', 'user3@example.com', '111-222-3333', 150, 'profile3.jpg', 'https://user3.website.com', '만나서 반가워요!', '자세한 소개 내용 4', NOW(), NOW(), 'ROLE_USER'),
    ('user4', 'password4', 'user4@example.com', '444-555-6666', 300, 'profile4.jpg', 'https://user4.website.com', '즐거운 하루!', '자세한 소개 내용 5', NOW(), NOW(), 'ROLE_USER'),
    ('user5', 'password5', 'user5@example.com', '777-888-9999', 400, 'profile5.jpg', 'https://user5.website.com', '행복한 하루 보내세요!', '자세한 소개 내용 6', NOW(), NOW(), 'ROLE_USER'),
    ('user6', 'password6', 'user6@example.com', '111-111-1111', 50, 'profile6.jpg', 'https://user6.website.com', '안녕하세요~', '자세한 소개 내용 7', NOW(), NOW(), 'ROLE_USER'),
    ('user7', 'password7', 'user7@example.com', '222-222-2222', 600, 'profile7.jpg', 'https://user7.website.com', '행복한 하루 되세요!', '자세한 소개 내용 8', NOW(), NOW(), 'ROLE_USER'),
    ('user8', 'password8', 'user8@example.com', '333-333-3333', 250, 'profile8.jpg', 'https://user8.website.com', '잘 지내고 계신가요?', '자세한 소개 내용 9', NOW(), NOW(), 'ROLE_USER'),
    ('user9', 'password9', 'user9@example.com', '444-444-4444', 750, 'profile9.jpg', 'https://user9.website.com', '즐거운 하루 되세요~', '자세한 소개 내용 10', NOW(), NOW(), 'ROLE_USER'),
    ('user10', 'password10', 'user10@example.com', '555-555-5555', 900, 'profile10.jpg', 'https://user10.website.com', '만나서 반가워요~', '자세한 소개 내용 11', NOW(), NOW(), 'ROLE_USER');


INSERT INTO chat_room (room_name, create_at, modified_at)
VALUES
    ('방 이름 1', NOW(), NOW()),
    ('방 이름 2', NOW(), NOW()),
    ('방 이름 3', NOW(), NOW()),
    ('방 이름 4', NOW(), NOW()),
    ('방 이름 5', NOW(), NOW()),
    ('방 이름 6', NOW(), NOW()),
    ('방 이름 7', NOW(), NOW()),
    ('방 이름 8', NOW(), NOW()),
    ('방 이름 9', NOW(), NOW()),
    ('방 이름 10', NOW(), NOW());

INSERT INTO chat_room_user (chat_room_id, user_id, create_at, modified_at)
VALUES
    (1, 1, NOW(), NOW()),
    (1, 2, NOW(), NOW()),
    (2, 1, NOW(), NOW()),
    (2, 3, NOW(), NOW()),
    (3, 2, NOW(), NOW()),
    (3, 3, NOW(), NOW()),
    (4, 1, NOW(), NOW()),
    (4, 4, NOW(), NOW()),
    (5, 3, NOW(), NOW()),
    (5, 5, NOW(), NOW()),
    (6, 2, NOW(), NOW()),
    (6, 4, NOW(), NOW()),
    (7, 5, NOW(), NOW()),
    (7, 1, NOW(), NOW()),
    (8, 3, NOW(), NOW()),
    (8, 2, NOW(), NOW()),
    (9, 4, NOW(), NOW()),
    (9, 1, NOW(), NOW()),
    (10, 5, NOW(), NOW()),
    (10, 3, NOW(), NOW())
    ON DUPLICATE KEY UPDATE chat_room_user_id = chat_room_user_id;
;

INSERT INTO messages (chat_room_user_id, message, create_at, modified_at)
VALUES
    (1, '안녕하세요!', '2023-08-24 10:00:00', '2023-08-24 10:00:00'),
    (2, '반갑습니다!', '2023-08-24 11:00:00', '2023-08-24 11:00:00'),
    (3, '오늘 날씨가 좋네요.', '2023-08-24 12:00:00', '2023-08-24 12:00:00'),
    (1, '뭐해요?', '2023-08-24 13:00:00', '2023-08-24 13:00:00'),
    (2, '공부 중입니다.', '2023-08-24 14:00:00', '2023-08-24 14:00:00'),
    (3, '나도요!', '2023-08-24 15:00:00', '2023-08-24 15:00:00'),
    (1, '저녁 메뉴 추천 좀 해주세요.', '2023-08-24 16:00:00', '2023-08-24 16:00:00'),
    (2, '피자 어때요?', '2023-08-24 17:00:00', '2023-08-24 17:00:00'),
    (3, '한식이 땡기네요.', '2023-08-24 18:00:00', '2023-08-24 18:00:00'),
    (1, '나랑 같이 영화 볼래요?', '2023-08-24 19:00:00', '2023-08-24 19:00:00'),
    (2, '좋아요!', '2023-08-24 20:00:00', '2023-08-24 20:00:00'),
    (3, '저도 가고 싶어요.', '2023-08-24 21:00:00', '2023-08-24 21:00:00'),
    (1, '오늘 일정 어때요?', '2023-08-24 22:00:00', '2023-08-24 22:00:00'),
    (2, '조금 바쁘네요.', '2023-08-24 23:00:00', '2023-08-24 23:00:00'),
    (3, '여유로워요!', '2023-08-25 00:00:00', '2023-08-25 00:00:00'),
    (1, '주말에 뭐 할까요?', '2023-08-25 01:00:00', '2023-08-25 01:00:00'),
    (2, '산책하면 좋을 것 같아요.', '2023-08-25 02:00:00', '2023-08-25 02:00:00'),
    (3, '영화나 보러 갈까요?', '2023-08-25 03:00:00', '2023-08-25 03:00:00'),
    (1, '휴식이 필요해요.', '2023-08-25 04:00:00', '2023-08-25 04:00:00'),
    (2, '마사지 예약해보세요!', '2023-08-25 05:00:00', '2023-08-25 05:00:00');

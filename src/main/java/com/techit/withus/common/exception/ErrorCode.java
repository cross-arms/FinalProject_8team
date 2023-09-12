package com.techit.withus.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_001", "서버에 오류가 발생하였습니다."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "AR_001", "권한이 없습니다."),
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "AU_001", "인증에 실패하였습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C_001", "유효하지 않은 입력입니다."),
    NOT_EMPTY(HttpStatus.BAD_REQUEST, "C_002", "값이 필요합니다."),
    INVALID_STATE(HttpStatus.BAD_REQUEST, "C_003", "유효하지 않은 상태입니다."),

    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "M_001", "유효하지 않은 이메일 형식입니다."),
    INVALID_PHONE_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "M_002", "유효하지 않은 전화번호 형식입니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "M_003", "이미 '%s'라는 이메일로 가입된 계정이 존재합니다."),
    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M_004", "멤버(id = %d)가 존재하지 않습니다."),
    INVALID_CODE_FORMAT(HttpStatus.BAD_REQUEST, "M_005", "인증번호가 일치하지 않습니다."),

    CHATROOM_NOT_EXIST(HttpStatus.BAD_REQUEST, "CR_001", "채팅 방(id = %d)가 존재하지 않습니다."),
    INVALID_ROOM_NAME(HttpStatus.BAD_REQUEST, "CR_002", "채팅 방의 이름은 20자를 넘을 수 없습니다. (%s)"),
    CHATROOM_USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "CRU_001", "ChatRoomUser 엔티티를 찾을 수 없습니다."),
    INVALID_ROOM_MEMBER(HttpStatus.BAD_REQUEST, "CR_003", "채팅방은 적어도 한명의 유저를 포함해야 합니다."),

    FEED_NOT_FOUND(HttpStatus.BAD_REQUEST, "F_001", "피드 정보가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CT_001", "카테고리 정보가 존재하지 않습니다."),
    ALREADY_LIKE(HttpStatus.BAD_REQUEST, "FL_001", "이미 좋아요 하신 피드입니다."),

    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "T_001", "토큰이 존재하지 않습니다."),
    COOKIE_MISSING(HttpStatus.BAD_REQUEST, "CK_001", "쿠키가 누락되었습니다."),

    DIRECTORY_CREATION_FAILED(HttpStatus.BAD_REQUEST, "IM_001", "파일 저장 경로를 생성하지 못하였습니다."),
    NOT_FOUND_FILE_TYPE(HttpStatus.BAD_REQUEST, "IM_002", "정의되지 않은 파일 타입입니다."),

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "CM_001", "댓글 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

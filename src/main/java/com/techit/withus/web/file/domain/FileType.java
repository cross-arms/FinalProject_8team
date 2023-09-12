package com.techit.withus.web.file.domain;

import com.techit.withus.common.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.techit.withus.common.exception.ErrorCode.NOT_FOUND_FILE_TYPE;

@Getter
@AllArgsConstructor
public enum FileType {

    FEED,
    USER_PROFILE,
    ;

    public static FileType findByType(FileType fileType) {
        return Arrays.stream(FileType.values())
                .filter(type -> type == fileType)
                .findFirst().orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_FILE_TYPE));
    }
}

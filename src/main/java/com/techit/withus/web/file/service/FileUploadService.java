package com.techit.withus.web.file.service;

import com.techit.withus.web.feeds.domain.entity.feed.Images;
import com.techit.withus.web.feeds.exception.ImageException;
import com.techit.withus.web.feeds.service.feed.FeedService;
import com.techit.withus.web.file.domain.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.techit.withus.common.exception.ErrorCode.DIRECTORY_CREATION_FAILED;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadService {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm");
    public static final String BASE_DIRECTORY = "media/%s/";

    private final FeedService feedService;

    /**
     * 일반 피드 이미지 업로드 수행
     *
     * @param images
     * @return
     */
    public List<Images> uploadImage(List<MultipartFile> images, FileType fileType) {
        log.info("upload image. fileType: {}", fileType);
        // 디렉터리 경로를 생성합니다.
        String fullPath = findBaseDir(fileType);

        try {
            Files.createDirectories(Path.of(fullPath));
        } catch (IOException e) {
            throw new ImageException(DIRECTORY_CREATION_FAILED);
        }

        ArrayList<Images> imageList = new ArrayList<>();
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();

            String[] fileNameSplit = originalFilename.split("\\.");

            String profileFilename = fileNameSplit[0] + "." + fileNameSplit[fileNameSplit.length - 1];

            // 2-3. 폴더와 파일 경로를 포함한 이름 만들기
            String profilePath = fullPath + profileFilename;

            // 3. MultipartFile 을 저장하기
            try {
                image.transferTo(Path.of(profilePath));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            imageList.add(Images.builder()
                    .imageURL(String.format("/static/%s/%s", fileType, profileFilename))
                    .build());
        }

        return imageList;
    }

    private String findBaseDir(FileType fileType) {
        FileType findFileType = FileType.findByType(fileType);

        return String.format(BASE_DIRECTORY, findFileType.name());
    }
}
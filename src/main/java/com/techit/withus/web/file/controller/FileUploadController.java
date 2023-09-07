package com.techit.withus.web.file.controller;

import com.techit.withus.web.feeds.domain.entity.feed.Images;
import com.techit.withus.web.file.domain.FileType;
import com.techit.withus.web.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(
            value = "/api/v1/feed/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<Images> fileUpload(
            @RequestParam("images") List<MultipartFile> multipartFiles
    ) {
        return fileUploadService.uploadImage(multipartFiles, FileType.FEED);
    }
}

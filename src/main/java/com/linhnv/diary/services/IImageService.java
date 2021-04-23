package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.responses.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

public interface IImageService {
    ResponseEntity<SystemResponse<ImageResponse>> upload(MultipartFile[] files);

    ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws FileNotFoundException;
}

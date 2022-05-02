package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.responses.ImageResponse;
import com.linhnv.diary.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService service;

    @PostMapping()
    private ResponseEntity<SystemResponse<ImageResponse>> upload(@RequestParam("files") MultipartFile[] files) {
        return service.upload(files);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws FileNotFoundException {
        return service.download(fileName, request);
    }

}

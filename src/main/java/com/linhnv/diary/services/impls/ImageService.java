package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.FileStorageProperties;
import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.responses.FileInfo;
import com.linhnv.diary.models.responses.ImageResponse;
import com.linhnv.diary.repositories.ImageRepository;
import com.linhnv.diary.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    private static final Set<String> extendValid = new HashSet<>(Arrays.asList("image/png", "image/jpeg", "image/bmp", "image/gif"));

    @Override
    public ResponseEntity<SystemResponse<ImageResponse>> upload(MultipartFile[] files) {

        for (MultipartFile file : files)
            if (!extendValid.contains(file.getContentType()))
                return Response.badRequest("File is invalid");

        List<FileInfo> fileInfos = save(files);

        ImageResponse imageResponse = new ImageResponse(fileInfos);

        return Response.ok(imageResponse);
    }

    private List<FileInfo> save(MultipartFile[] files) {
        return Arrays.stream(files).parallel()
                .map(it -> {
                    String fileName = storeFile(it);
                    String url = createUrl(fileName);

                    return map(fileName, url);
                }).collect(Collectors.toList());
    }

    private String storeFile(MultipartFile file) {

        Path storageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        if (!Files.exists(storageLocation)) {
            try {
                Files.createDirectories(storageLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileName = generateFileName(file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, storageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private FileInfo map(String fileName, String url) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(fileName);
        fileInfo.setUrl(url);

        return fileInfo;
    }

    private String createUrl(String fileName) {
        return fileStorageProperties.getDomain() + "/images/" + fileName;
    }

    private String generateFileName(String originalFilename) {
        return Calendar.getInstance().getTime().getTime() + originalFilename;
    }

    public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws FileNotFoundException {

        Resource resource = loadFileAsResource(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return ResponseEntity.badRequest().build();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private Resource loadFileAsResource(String fileName) throws FileNotFoundException {

        Path storageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Path filePath = storageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }

            throw new FileNotFoundException("File not found !");
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found !");
        }

    }
}

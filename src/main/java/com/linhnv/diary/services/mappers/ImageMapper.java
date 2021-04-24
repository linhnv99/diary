package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.entities.Image;
import com.linhnv.diary.models.responses.FileInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {

    public Image map(FileInfo fileInfo, String articleId) {

        Image image = new Image();
        image.setArticleId(articleId);
        image.setName(fileInfo.getName());
        image.setUrl(fileInfo.getUrl());
        image.setStatus(StatusEnum.ACTIVE);

        return image;
    }

    public List<FileInfo> map(List<Image> images) {
        return images.stream()
                .map(it -> {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setUrl(it.getUrl());
                    fileInfo.setName(it.getName());

                    return fileInfo;
                }).collect(Collectors.toList());
    }
}

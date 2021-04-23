package com.linhnv.diary.models.responses;

import java.util.List;

public class ImageResponse {
    List<FileInfo> fileInfos;

    public ImageResponse(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }
}

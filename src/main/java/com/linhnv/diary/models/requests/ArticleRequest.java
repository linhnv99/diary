package com.linhnv.diary.models.requests;

import com.linhnv.diary.models.responses.FileInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class ArticleRequest {

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    @NotNull
    private int feelingId;

    private boolean isFavorite;

    @NotNull
    @Size(min = 1)
    private Set<String> topics;

    private List<FileInfo> images;

    public ArticleRequest() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFeelingId() {
        return feelingId;
    }

    public void setFeelingId(int feelingId) {
        this.feelingId = feelingId;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }

    public List<FileInfo> getImages() {
        return images;
    }

    public void setImages(List<FileInfo> images) {
        this.images = images;
    }
}

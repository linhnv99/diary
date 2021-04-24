package com.linhnv.diary.models.requests;

import com.linhnv.diary.models.responses.FileInfo;

import java.util.List;
import java.util.Set;

public class ArticleUpdateRq {

    private String subject;

    private String content;

    private Integer feelingId;

    private Boolean isFavorite;

    private Set<String> topics;

    private List<FileInfo> images;

    public ArticleUpdateRq() {
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

    public Integer getFeelingId() {
        return feelingId;
    }

    public void setFeelingId(Integer feelingId) {
        this.feelingId = feelingId;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean favorite) {
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

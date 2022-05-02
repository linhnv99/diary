package com.linhnv.diary.models.responses;

import java.time.ZonedDateTime;
import java.util.List;

public class ArticleResponse {
    private String id;
    private String subject;
    private String content;
    private int feelingId;
    private boolean isFavorite;
    private ZonedDateTime createdAt;
    private String status;
    private List<TopicResponse> topics;
    private List<FileInfo> images;

    public ArticleResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TopicResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicResponse> topics) {
        this.topics = topics;
    }

    public List<FileInfo> getImages() {
        return images;
    }

    public void setImages(List<FileInfo> images) {
        this.images = images;
    }
}

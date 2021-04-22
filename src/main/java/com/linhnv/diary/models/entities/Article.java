package com.linhnv.diary.models.entities;

import com.linhnv.diary.models.bos.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
public class Article extends AbsEntity {

    @Column(columnDefinition = "VARCHAR(500)")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_favorite", columnDefinition = "TINYINT(1) default false")
    private boolean isFavorite;

    @Column(name = "status", columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;

    @Column(name = "feeling_id", columnDefinition = "VARCHAR(64)")
    private int feelingId;

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status.name();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFeelingId() {
        return feelingId;
    }

    public void setFeelingId(int feelingId) {
        this.feelingId = feelingId;
    }
}

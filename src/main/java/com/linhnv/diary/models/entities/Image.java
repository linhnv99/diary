package com.linhnv.diary.models.entities;

import com.linhnv.diary.models.bos.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String name;

    @Column(columnDefinition = "VARCHAR(500)")
    private String url;

    @Column(name = "status",columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    @Column(name = "article_id", columnDefinition = "VARCHAR(64)")
    private String articleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status.name();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}

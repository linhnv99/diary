package com.linhnv.diary.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "articles_topics")
public class ArticleTopic {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(64)")
    private String articleId;

    @Column(columnDefinition = "VARCHAR(64)")
    private String topicId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}

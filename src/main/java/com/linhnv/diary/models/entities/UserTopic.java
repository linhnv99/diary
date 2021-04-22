package com.linhnv.diary.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_topics")
public class UserTopic {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(64)")
    private String userId;

    @Column(columnDefinition = "VARCHAR(64)")
    private String topicId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}

package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.entities.UserTopic;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.responses.TopicResponse;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public Topic map(TopicCreate topicCreate) {
        Topic topic = new Topic();

        topic.setName(topicCreate.getName());
        topic.setDescription(topicCreate.getDescription());
        topic.setStatus(StatusEnum.ACTIVE);

        return topic;
    }

    public TopicResponse map(Topic topic) {
        TopicResponse response = new TopicResponse();

        response.setId(topic.getId());
        response.setName(topic.getName());
        response.setDescription(topic.getDescription());
        response.setDefault(topic.isDefault());
        response.setStatus(topic.getStatus());

        return response;
    }

    public UserTopic map(String userId, String topicId) {
        UserTopic userTopic = new UserTopic();

        userTopic.setUserId(userId);
        userTopic.setTopicId(topicId);

        return userTopic;
    }
}
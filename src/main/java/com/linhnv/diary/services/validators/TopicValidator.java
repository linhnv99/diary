package com.linhnv.diary.services.validators;

import com.linhnv.diary.models.bos.*;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.repositories.TopicRepository;
import com.linhnv.diary.repositories.UserRepository;
import com.linhnv.diary.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TopicValidator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository repository;

    public ResponseEntity<SystemResponse<TopicResponse>> validate(UserJwt userJwt) {
        User user = userRepository.findByIdAndStatus(userJwt.getId(), AccountStatus.ACTIVE.name());

        if (user == null)
            return Response.badRequest(StringResponse.UNAUTHORIZED);

        return Response.ok();
    }

    public ResponseEntity<SystemResponse<TopicResponse>> validate(Topic topic) {

        if (topic == null)
            return Response.badRequest(StringResponse.TOPIC_INVALID);

        return Response.ok();
    }

    public ResponseEntity<SystemResponse<TopicResponse>> validate(Topic topic, UserJwt userJwt) {
        User user = userRepository.findByIdAndStatus(userJwt.getId(), StatusEnum.ACTIVE.name());

        if (topic == null) return Response.badRequest(StringResponse.TOPIC_INVALID);

        if (user == null) return Response.badRequest(StringResponse.INVALID_ACCOUNT);

        if (!RoleEnum.ADMIN.name().equals(user.getRole()) && topic.isDefault())
            return Response.badRequest(StringResponse.UNAUTHORIZED);

        repository.deleteById(topic.getId());

        return Response.ok();
    }
}

package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.bos.UserJwt;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.entities.UserTopic;
import com.linhnv.diary.models.requests.TopicChangeDefault;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.repositories.TopicRepository;
import com.linhnv.diary.models.bos.*;
import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.models.requests.TopicUpdateRq;
import com.linhnv.diary.repositories.UserRepository;
import com.linhnv.diary.repositories.UserTopicRepository;
import com.linhnv.diary.services.ITopicService;
import com.linhnv.diary.services.jwts.JwtUser;
import com.linhnv.diary.services.mappers.TopicMapper;
import com.linhnv.diary.services.validators.TopicValidator;
import com.linhnv.diary.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private JwtUser jwtUser;

    @Autowired
    private TopicRepository repository;

    @Autowired
    private UserTopicRepository userTopicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicMapper mapper;

    @Autowired
    private TopicValidator validator;


    @Override
    public ResponseEntity<SystemResponse<TopicResponse>> create(TopicCreate topicCreate, HttpServletRequest request) {

        Topic topic = mapper.map(topicCreate);

        topic = repository.save(topic);

        UserJwt userJwt = (UserJwt) jwtUser.getClaims(request);

        ResponseEntity<SystemResponse<TopicResponse>> validate = validator.validate(userJwt);
        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        UserTopic userTopic = mapper.map(userJwt.getId(), topic.getId());

        userTopicRepository.save(userTopic);

        TopicResponse topicResponse = mapper.map(topic);

        return Response.ok(topicResponse);
    }

    @Override
    public ResponseEntity<SystemResponse<TopicResponse>> update(String topicId, TopicUpdateRq topicUpdateRq) {

        Topic topic = repository.findByIdAndStatus(topicId, StatusEnum.ACTIVE.name());

        ResponseEntity<SystemResponse<TopicResponse>> validate = validator.validate(topic);

        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        topic = mapper.map(topicUpdateRq);

        repository.save(topic);

        return Response.ok();
    }

    /**
     * Change default topic (for ADMIN)
     *
     * @param topicChangeDefault
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<SystemResponse<TopicResponse>> changeDefault(TopicChangeDefault topicChangeDefault, HttpServletRequest request) {

        Topic topic = repository.findByIdAndStatus(topicChangeDefault.getId(), StatusEnum.ACTIVE.name());

        ResponseEntity<SystemResponse<TopicResponse>> validate = validator.validate(topic);
        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        topic.setDefault(topicChangeDefault.getIsDefault());

        repository.save(topic);

        return Response.ok();
    }

    /**
     * Delete topic <p>
     * when default = true (Role = ADMIN) <p>
     * otherwise (Role = USER)
     *
     * @param topicId
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<SystemResponse<TopicResponse>> delete(String topicId, HttpServletRequest request) {

        Topic topic = repository.findByIdAndStatus(topicId, StatusEnum.ACTIVE.name());

        UserJwt userJwt = (UserJwt) jwtUser.getClaims(request);

        ResponseEntity<SystemResponse<TopicResponse>> validate = validator.validate(topic, userJwt);

        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        return Response.ok();
    }

    @Override
    public ResponseEntity<SystemResponse<TopicResponse>> getDetail(String topicId) {

        Topic topic = repository.findByIdAndStatus(topicId, StatusEnum.ACTIVE.name());

        ResponseEntity<SystemResponse<TopicResponse>> validate = validator.validate(topic);

        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        TopicResponse topicResponse = mapper.map(topic);

        return Response.ok(topicResponse);
    }

    /**
     * Get all topic by each account
     *
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<SystemResponse<List<TopicResponse>>> getAll(HttpServletRequest request) {

        UserJwt userJwt = (UserJwt) jwtUser.getClaims(request);
        User user = userRepository.findByIdAndStatus(userJwt.getId(), AccountStatus.ACTIVE.name());

        if (user == null) return Response.badRequest(StringResponse.INVALID_ACCOUNT);

        List<UserTopic> userTopics = userTopicRepository.findByUserId(user.getId());

        Set<String> topicIds = getTopicIdsFrom(userTopics);

        List<Topic> topics = repository.findAllById(topicIds);

        List<TopicResponse> topicResponses = mapper.map(topics);

        return Response.ok(topicResponses);
    }

    private Set<String> getTopicIdsFrom(List<UserTopic> userTopics) {
        return userTopics.stream()
                .map(UserTopic::getTopicId)
                .collect(Collectors.toSet());
    }
}

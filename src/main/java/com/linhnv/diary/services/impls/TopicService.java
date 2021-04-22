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

@Service
public class TopicService implements ITopicService {

    @Autowired
    private JwtUser jwtUser;

    @Autowired
    private TopicRepository repository;

    @Autowired
    private UserTopicRepository userTopicRepository;

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

    /**
     * Change default topic (for ADMIN)
     *
     * @param topicChangeDefault
     * @param request
     * @return
     * */
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
}

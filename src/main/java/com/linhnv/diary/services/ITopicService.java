package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.TopicChangeDefault;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.requests.TopicUpdateRq;
import com.linhnv.diary.models.responses.TopicResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ITopicService {
    ResponseEntity<SystemResponse<TopicResponse>> create(TopicCreate topicCreate, HttpServletRequest request);

    ResponseEntity<SystemResponse<TopicResponse>> changeDefault(TopicChangeDefault topicChangeDefault, HttpServletRequest request);

    ResponseEntity<SystemResponse<TopicResponse>> update(String topicId, TopicUpdateRq topicUpdateRq);

    ResponseEntity<SystemResponse<TopicResponse>> delete(String topicId, HttpServletRequest request);

    ResponseEntity<SystemResponse<TopicResponse>> getDetail(String topicId);

    ResponseEntity<SystemResponse<List<TopicResponse>>> getAll(HttpServletRequest request);
}

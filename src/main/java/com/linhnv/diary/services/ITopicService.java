package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.TopicChangeDefault;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.responses.TopicResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface ITopicService {
    ResponseEntity<SystemResponse<TopicResponse>> create(TopicCreate topicCreate, HttpServletRequest request);

    ResponseEntity<SystemResponse<TopicResponse>> changeDefault(TopicChangeDefault topicChangeDefault, HttpServletRequest request);
}

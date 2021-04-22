package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.requests.TopicChangeDefault;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.services.ITopicService;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private ITopicService service;

    @PostMapping()
    public ResponseEntity<SystemResponse<TopicResponse>> create(@Valid @RequestBody TopicCreate topicCreate, HttpServletRequest request) {
        return service.create(topicCreate, request);
    }

    @PutMapping("/defaults/changers")
    public ResponseEntity<SystemResponse<TopicResponse>> changeDefault(@Valid @RequestBody TopicChangeDefault topicChangeDefault, HttpServletRequest request) {
        return service.changeDefault(topicChangeDefault, request);
    }


}

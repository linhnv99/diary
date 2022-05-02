package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.TopicCreate;
import com.linhnv.diary.models.requests.TopicChangeDefault;
import com.linhnv.diary.models.requests.TopicUpdateRq;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.services.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private ITopicService service;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<TopicResponse>>> getAll(HttpServletRequest request) {
        return service.getAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<TopicResponse>> getDetail(@NotBlank @PathVariable("id") String topicId) {
        return service.getDetail(topicId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<TopicResponse>> update(@NotBlank @PathVariable("id") String topicId,
                                                                @Valid @RequestBody TopicUpdateRq topicUpdateRq) {
        return service.update(topicId, topicUpdateRq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<TopicResponse>> delete(@NotBlank @PathVariable("id") String topicId, HttpServletRequest request) {
        return service.delete(topicId, request);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<TopicResponse>> create(@Valid @RequestBody TopicCreate topicCreate, HttpServletRequest request) {
        return service.create(topicCreate, request);
    }

    @PutMapping("/defaults/changers")
    public ResponseEntity<SystemResponse<TopicResponse>> changeDefault(@Valid @RequestBody TopicChangeDefault topicChangeDefault, HttpServletRequest request) {
        return service.changeDefault(topicChangeDefault, request);
    }

}

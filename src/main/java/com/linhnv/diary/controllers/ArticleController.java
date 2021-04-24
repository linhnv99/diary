package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.services.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService service;

    @PostMapping()
    public ResponseEntity<SystemResponse<ArticleResponse>> create(@Valid @RequestBody ArticleRequest articleRequest,
                                                                  HttpServletRequest request) {
        return service.create(articleRequest, request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<ArticleResponse>> update(@Valid @RequestBody ArticleUpdateRq articleUpdateRq,
                                                                  @NotBlank @PathVariable("id") String articleId, HttpServletRequest request) {
        return service.update(articleId, articleUpdateRq, request);
    }

}

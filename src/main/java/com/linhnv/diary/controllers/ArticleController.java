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
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService service;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<ArticleResponse>>> getAll(HttpServletRequest request) {
        return service.getAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<ArticleResponse>> getDetail(@NotBlank @PathVariable("id") String articleId,
                                                                     HttpServletRequest request) {
        return service.getDetail(articleId, request);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<ArticleResponse>> create(@Valid @RequestBody ArticleRequest articleRequest,
                                                                  HttpServletRequest request) {
        return service.create(articleRequest, request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<ArticleResponse>> update(@Valid @RequestBody ArticleUpdateRq articleUpdateRq,
                                                                  @NotBlank @PathVariable("id") String articleId,
                                                                  HttpServletRequest request) {
        return service.update(articleId, articleUpdateRq, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<ArticleResponse>> delete(@NotBlank @PathVariable("id") String articleId,
                                                                  HttpServletRequest request) {
        return service.delete(articleId, request);
    }

}

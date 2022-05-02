package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.FilterArticle;
import com.linhnv.diary.models.bos.Paging;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.services.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService service;


    @GetMapping("/filters")
    public ResponseEntity<SystemResponse<Paging<List<ArticleResponse>>>> filter
            (HttpServletRequest request,
             @RequestParam(value = "subject", required = false) String subject,
             @RequestParam(value = "feeling", required = false, defaultValue = "0") int feeling,
             @RequestParam(value = "startAt", required = false)
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startAt,
             @Min(1) @RequestParam(value = "page", defaultValue = "1") int page) {

        FilterArticle filterArticle = new FilterArticle();
        filterArticle.setSubject(subject);
        filterArticle.setFeeling(feeling);
        filterArticle.setStartAt(startAt);
        filterArticle.setPage(page);
        filterArticle.setRequest(request);

        return service.filter(filterArticle);
    }


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

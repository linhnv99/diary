package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IArticleService {
    ResponseEntity<SystemResponse<ArticleResponse>> create(ArticleRequest articleRequest, HttpServletRequest request);

    ResponseEntity<SystemResponse<ArticleResponse>> update(String articleId, ArticleUpdateRq articleUpdateRq, HttpServletRequest request);
}

package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.entities.Article;
import com.linhnv.diary.models.entities.ArticleTopic;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.models.responses.FileInfo;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleMapper {

    public Article map(ArticleRequest articleRequest, String userId) {

        Article article = new Article();

        article.setSubject(articleRequest.getSubject());
        article.setContent(articleRequest.getContent());
        article.setFeelingId(articleRequest.getFeelingId());
        article.setFavorite(articleRequest.getIsFavorite());
        article.setUserId(userId);
        article.setStatus(StatusEnum.ACTIVE);

        return article;
    }

    public ArticleResponse map(Article article, List<TopicResponse> topicResponses, List<FileInfo> imagesResponse) {

        ArticleResponse response = new ArticleResponse();

        response.setId(article.getId());
        response.setSubject(article.getSubject());
        response.setContent(article.getContent());
        response.setFeelingId(article.getFeelingId());
        response.setIsFavorite(article.isFavorite());
        response.setCreatedAt(article.getCreatedAt());
        response.setStatus(article.getStatus());
        response.setTopics(topicResponses);
        response.setImages(imagesResponse);

        return response;
    }


    public ArticleTopic map(String topicId, String articleId) {

        ArticleTopic articleTopic = new ArticleTopic();

        articleTopic.setTopicId(topicId);
        articleTopic.setArticleId(articleId);

        return articleTopic;
    }

    public Article map(Article article, ArticleUpdateRq articleUpdateRq) {

        if (Utils.notNullAndEmpty(articleUpdateRq.getSubject()))
            article.setSubject(articleUpdateRq.getSubject());

        if (Utils.notNullAndEmpty(articleUpdateRq.getContent()))
            article.setContent(articleUpdateRq.getContent());

        if (articleUpdateRq.getIsFavorite() != null)
            article.setFavorite(articleUpdateRq.getIsFavorite());

        if (articleUpdateRq.getFeelingId() != null)
            article.setFeelingId(articleUpdateRq.getFeelingId());

        article.setStatus(StatusEnum.ACTIVE);

        return article;
    }
}

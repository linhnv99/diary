package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.entities.Article;
import com.linhnv.diary.models.entities.ArticleTopic;
import com.linhnv.diary.models.entities.Image;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.models.responses.FileInfo;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private ArticleTopicMapper articleTopicMapper;

    @Autowired
    private ImageMapper imageMapper;

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

    public ArticleResponse map(Article article) {

        ArticleResponse articleResponse = new ArticleResponse();

        articleResponse.setId(article.getId());
        articleResponse.setSubject(article.getSubject());
        articleResponse.setFeelingId(article.getFeelingId());
        articleResponse.setContent(article.getContent());
        articleResponse.setIsFavorite(article.isFavorite());
        articleResponse.setCreatedAt(article.getCreatedAt());
        articleResponse.setStatus(article.getStatus());

        return articleResponse;
    }

    public ArticleResponse map(Article article, List<ArticleTopic> articleTopics, List<Topic> topics, List<Image> images) {
        ArticleResponse articleResponse = map(article);

        Set<String> topicIds = articleTopicMapper.map(article.getId(), articleTopics);
        articleResponse.setTopics(topicMapper.map(topicIds, topics));


        List<Image> imageOfArticle = images.stream()
                .filter(image -> image.getArticleId().equals(article.getId()))
                .collect(Collectors.toList());

        articleResponse.setImages(imageMapper.map(imageOfArticle));

        return articleResponse;
    }

    public List<ArticleResponse> map(List<Article> articles, List<ArticleTopic> articleTopics, List<Topic> topics, List<Image> images) {
        return articles.stream()
                .map(it -> map(it, articleTopics, topics, images))
                .collect(Collectors.toList());
    }
}

package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.entities.ArticleTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleTopicMapper {

    public Set<String> map(String articleId, List<ArticleTopic> articleTopics) {
        return articleTopics.stream()
                .filter(it -> it.getArticleId().equals(articleId))
                .map(ArticleTopic::getTopicId)
                .collect(Collectors.toSet());
    }
}

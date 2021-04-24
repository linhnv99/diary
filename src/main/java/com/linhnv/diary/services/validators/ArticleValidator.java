package com.linhnv.diary.services.validators;

import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.entities.Article;
import com.linhnv.diary.models.entities.Feeling;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.repositories.FeelingRepository;
import com.linhnv.diary.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleValidator {
    @Autowired
    private FeelingRepository feelingRepository;

    public ResponseEntity<SystemResponse<ArticleResponse>> validate(ArticleRequest articleRequest, List<Topic> topics) {

        Feeling feeling = feelingRepository.findByIdAndStatus(articleRequest.getFeelingId(), StatusEnum.ACTIVE.name());

        if (feeling == null)
            return Response.badRequest(StringResponse.INVALID_FEELING);

        if (topics.size() != articleRequest.getTopics().size())
            return Response.badRequest(StringResponse.TOPIC_INVALID);

        return Response.ok();
    }

    public ResponseEntity<SystemResponse<ArticleResponse>> validate(Article article, ArticleUpdateRq articleUpdateRq, String userId) {

        if (article == null) return Response.badRequest(StringResponse.INVALID_ARTICLE);

        if (!article.getUserId().equals(userId)) return Response.badRequest(StringResponse.UNAUTHORIZED);

        if (articleUpdateRq.getFeelingId() != null) {

            Feeling feeling = feelingRepository.findByIdAndStatus(articleUpdateRq.getFeelingId(), StatusEnum.ACTIVE.name());
            if (feeling == null) return Response.badRequest(StringResponse.INVALID_FEELING);

        }
        return Response.ok();
    }
}

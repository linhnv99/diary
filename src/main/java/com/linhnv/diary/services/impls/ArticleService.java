package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.bos.UserJwt;
import com.linhnv.diary.models.entities.*;
import com.linhnv.diary.models.requests.ArticleRequest;
import com.linhnv.diary.models.requests.ArticleUpdateRq;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.models.responses.FileInfo;
import com.linhnv.diary.models.responses.TopicResponse;
import com.linhnv.diary.repositories.*;
import com.linhnv.diary.services.IArticleService;
import com.linhnv.diary.services.jwts.JwtUser;
import com.linhnv.diary.services.mappers.ArticleMapper;
import com.linhnv.diary.services.mappers.ImageMapper;
import com.linhnv.diary.services.mappers.TopicMapper;
import com.linhnv.diary.services.validators.ArticleValidator;
import com.linhnv.diary.utils.StringResponse;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ArticleTopicRepository articleTopicRepo;

    @Autowired
    private ArticleValidator validator;

    @Autowired
    private ArticleMapper mapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private JwtUser jwtUser;

    @Autowired
    private FeelingRepository feelingRepository;


    @Override
    public ResponseEntity<SystemResponse<ArticleResponse>> create(ArticleRequest articleRequest, HttpServletRequest request) {

        UserJwt userJwt = (UserJwt) jwtUser.getClaims(request);

        List<Topic> topics = topicRepository.findAllByIdAndStatus(articleRequest.getTopics(), StatusEnum.ACTIVE.name());

        ResponseEntity<SystemResponse<ArticleResponse>> validate = validator.validate(articleRequest, topics);

        if (!validate.getStatusCode().is2xxSuccessful()) return validate;

        Article article = mapper.map(articleRequest, userJwt.getId());
        article = repository.save(article);

        List<ArticleTopic> articleTopics = getArticleTopic(topics, article.getId());
        articleTopicRepo.saveAll(articleTopics);

        List<FileInfo> imagesResponse = null;
        if (Utils.notNullAndEmpty(articleRequest.getImages())) {
            imagesResponse = saveImage(articleRequest.getImages(), article.getId());
        }

        ArticleResponse articleResponse = mapper.map(article, topicMapper.map(topics), imagesResponse);

        return Response.ok(articleResponse);
    }

    @Override
    public ResponseEntity<SystemResponse<ArticleResponse>> update(String articleId, ArticleUpdateRq articleUpdateRq, HttpServletRequest request) {

        UserJwt userJwt = (UserJwt) jwtUser.getClaims(request);

        Article article = repository.findByIdAndStatus(articleId, StatusEnum.ACTIVE.name());

        ResponseEntity<SystemResponse<ArticleResponse>> validate = validator.validate(article, articleUpdateRq, userJwt.getId());

        if (!validate.getStatusCode().is2xxSuccessful()) return validate;

        article = mapper.map(article, articleUpdateRq);

        article = repository.save(article);

        List<Topic> topics;
        if (articleUpdateRq.getTopics() != null) {
            topics = topicRepository.findAllByIdAndStatus(articleUpdateRq.getTopics(), StatusEnum.ACTIVE.name());

            if (topics.size() != articleUpdateRq.getTopics().size())
                return Response.badRequest(StringResponse.TOPIC_INVALID);

            articleTopicRepo.deleteByArticleId(articleId);

            List<ArticleTopic> articleTopics = getArticleTopic(topics, articleId);
            articleTopicRepo.saveAll(articleTopics);
        } else {
            topics = getOldTopics(articleId);
        }

        List<FileInfo> imagesResponse = getImagesResponse(articleUpdateRq.getImages(), articleId);

        ArticleResponse articleResponse = mapper.map(article, topicMapper.map(topics), imagesResponse);

        return Response.ok(articleResponse);
    }

    private List<FileInfo> getImagesResponse(List<FileInfo> images, String articleId) {
        List<FileInfo> imagesResponse;
        if (Utils.notNullAndEmpty(images)) {
            imageRepository.deleteByArticleId(articleId);
            imagesResponse = saveImage(images, articleId);
        } else {
            List<Image> imagesOld = imageRepository.findByArticleId(articleId);
            imagesResponse = imageMapper.map(imagesOld);
        }
        return imagesResponse;
    }

    private List<Topic> getOldTopics(String articleId) {
        List<ArticleTopic> articleTopics = articleTopicRepo.findByArticleId(articleId);
        Set<String> topicIds = articleTopics.stream().map(ArticleTopic::getTopicId).collect(Collectors.toSet());
        return topicRepository.findAllByIdAndStatus(topicIds, StatusEnum.ACTIVE.name());
    }

    private List<ArticleTopic> getArticleTopic(List<Topic> topics, String articleId) {
        return topics.stream()
                .map(it -> mapper.map(it.getId(), articleId))
                .collect(Collectors.toList());
    }

    private List<FileInfo> saveImage(List<FileInfo> imagesRequest, String articleId) {
        List<Image> images = imagesRequest.stream()
                .map(it -> imageMapper.map(it, articleId))
                .collect(Collectors.toList());

        images = imageRepository.saveAll(images);

        return imageMapper.map(images);
    }

}

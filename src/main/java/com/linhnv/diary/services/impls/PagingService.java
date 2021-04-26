package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.FilterArticle;
import com.linhnv.diary.models.bos.Paging;
import com.linhnv.diary.models.entities.Article;
import com.linhnv.diary.models.entities.ArticleTopic;
import com.linhnv.diary.models.entities.Image;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.models.responses.ArticleResponse;
import com.linhnv.diary.services.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class PagingService {
    @PersistenceContext
    EntityManager entityManager;

    @Value("${app.page.size}")
    private int pageSizeDefault;

    @Autowired
    private ArticleMapper mapper;

    public Paging<List<Article>> getListPaging(FilterArticle filterArticle, Root<Article> root, CriteriaQuery<Article> criteriaQuery,
                                               CriteriaQuery<Long> criteriaQuery1, CriteriaBuilder criteriaBuilder,
                                               List<Predicate> predicates) {

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        return getPaging(filterArticle, criteriaQuery, criteriaQuery1, predicates);
    }

    private Paging<List<Article>> getPaging(FilterArticle filterArticle, CriteriaQuery<Article> criteriaQuery,
                                            CriteriaQuery<Long> criteriaQuery1, List<Predicate> predicates) {
        int startIndex = (filterArticle.getPage() - 1) * pageSizeDefault;

        List<Article> articles = entityManager.createQuery(criteriaQuery)
                .setFirstResult(startIndex)
                .setMaxResults(pageSizeDefault)
                .getResultList();

        System.out.println("-------------");
        criteriaQuery1.where(predicates.toArray(new Predicate[0]));

        long totalElement = entityManager.createQuery(criteriaQuery1).getSingleResult();
        int totalPage = (int) Math.ceil((double) totalElement / pageSizeDefault);

        return mapPaging(articles, filterArticle.getPage(), pageSizeDefault,
                articles.size(), totalElement, totalPage);
    }

    private Paging<List<Article>> mapPaging(List<Article> articles, int page, int pageSizeDefault, int size,
                                            long totalElement, int totalPage) {

        Paging<List<Article>> paging = new Paging<>();

        paging.setPage(page);
        paging.setSize(pageSizeDefault);
        paging.setTotalInPage(size);
        paging.setTotalRecord(totalElement);
        paging.setTotalPage(totalPage);
        paging.setData(articles);

        return paging;
    }

    public Paging<List<ArticleResponse>> map(Paging<List<Article>> paging, List<ArticleTopic> articleTopics,
                                             List<Topic> topics, List<Image> images) {
        Paging<List<ArticleResponse>> pagingDTO = new Paging<>();

        pagingDTO.setPage(paging.getPage());
        pagingDTO.setSize(pageSizeDefault);
        pagingDTO.setTotalInPage(paging.getSize());
        pagingDTO.setTotalRecord(paging.getTotalRecord());
        pagingDTO.setTotalPage(paging.getTotalPage());
        pagingDTO.setData(mapper.map(paging.getData(), articleTopics, topics, images));

        return pagingDTO;
    }
}

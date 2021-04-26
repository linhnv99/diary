package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.FilterArticle;
import com.linhnv.diary.models.bos.Paging;
import com.linhnv.diary.models.entities.Article;
import com.linhnv.diary.models.entities.ArticleTopic;
import com.linhnv.diary.models.entities.Topic;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PagingService pagingService;

    public Paging<List<Article>> filter(FilterArticle filterArticle) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        CriteriaQuery<Long> criteriaQuery1 = criteriaBuilder.createQuery(Long.class);

        Root<Article> root = criteriaQuery.from(Article.class);

        criteriaQuery.select(root);
        criteriaQuery1.select(criteriaBuilder.count(criteriaQuery1.from(Article.class)));

        List<Predicate> predicates = buildPredicates(filterArticle, criteriaBuilder, root);

        return pagingService.getListPaging(filterArticle, root, criteriaQuery, criteriaQuery1, criteriaBuilder, predicates);
    }

    private List<Predicate> buildPredicates(FilterArticle filterArticle, CriteriaBuilder criteriaBuilder, Root<Article> root) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("userId"), filterArticle.getUserId()));

        if (Utils.notNullAndEmpty(filterArticle.getSubject())) {
            String subject = "%" + filterArticle.getSubject() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), subject));
        }

        if (filterArticle.getFeeling() != 0)
            predicates.add(criteriaBuilder.equal(root.get("feelingId"), filterArticle.getFeeling()));

        if (filterArticle.getStartAt() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filterArticle.getStartAt()));

        return predicates;
    }
}

package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.ArticleTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ArticleTopicRepository extends JpaRepository<ArticleTopic, Integer> {

    @Query(value = "DELETE FROM articles_topics at \n" +
            "WHERE at.article_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByArticleId(String articleId);

    List<ArticleTopic> findByArticleId(String articleId);
}

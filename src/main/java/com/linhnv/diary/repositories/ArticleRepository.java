package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    Article findByIdAndStatus(String articleId, String status);

    List<Article> findByUserIdAndStatus(String userId, String status);

    @Query(value = "UPDATE articles a \n" +
            "SET a.status = 'DELETED' \n" +
            "WHERE a.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(String id);
}

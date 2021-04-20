package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.ArticleTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTopicRepository extends JpaRepository<ArticleTopic, Integer> {
}

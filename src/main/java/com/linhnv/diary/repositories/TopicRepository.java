package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    Topic findByIdAndStatus(String topicId, String status);
}

package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Integer> {
    List<UserTopic> findByUserId(String userId);
}

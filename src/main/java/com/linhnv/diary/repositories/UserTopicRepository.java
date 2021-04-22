package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, Integer> {
}

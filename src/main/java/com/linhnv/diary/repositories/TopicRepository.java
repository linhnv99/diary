package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    Topic findByIdAndStatus(String topicId, String status);

    @Query(value = "UPDATE topics \n" +
            "SET status = 'DELETED' \n" +
            "WHERE id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(String topicId);
}

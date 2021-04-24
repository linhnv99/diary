package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    Topic findByIdAndStatus(String topicId, String status);

    @Query(value = "UPDATE topics \n" +
            "SET status = 'DELETED' \n" +
            "WHERE id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(String topicId);

    @Query("SELECT distinct t FROM Topic t WHERE (t.id in ?1 OR t.isDefault = true) AND t.status = 'ACTIVE'")
    List<Topic> findAllByIdAndDefaultTrue(Set<String> topicIds);

    @Query("SELECT t from Topic t \n" +
            "WHERE t.id IN ?1 AND t.status = ?2")
    List<Topic> findAllByIdAndStatus(Set<String> topics, String status);

}

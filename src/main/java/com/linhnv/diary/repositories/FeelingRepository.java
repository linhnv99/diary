package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.Feeling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeelingRepository extends JpaRepository<Feeling, Integer> {
}

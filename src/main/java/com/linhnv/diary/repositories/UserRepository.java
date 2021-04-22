package com.linhnv.diary.repositories;

import com.linhnv.diary.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsernameAndStatus(String username, String status);

}

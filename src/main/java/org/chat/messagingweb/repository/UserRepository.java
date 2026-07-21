package org.chat.messagingweb.repository;

import org.chat.messagingweb.domain.entity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // For autocomplete search in frontend mention input (@term)

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT(:query, '%'))")
    List<User> searchForMention(@Param("query") String query);
}

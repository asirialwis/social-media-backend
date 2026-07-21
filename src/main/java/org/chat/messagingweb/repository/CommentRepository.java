package org.chat.messagingweb.repository;

import org.chat.messagingweb.domain.entity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

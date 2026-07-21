package org.chat.messagingweb.domain.entity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chat.messagingweb.domain.common.BaseEntity;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;

    @Lob
    @Column(nullable = false)
    String content;
}

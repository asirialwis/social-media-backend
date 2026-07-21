package org.chat.messagingweb.domain.entity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.chat.messagingweb.domain.common.BaseEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false , unique = true , length = 50)
    private String username;

    @Column(nullable = false , unique = true , length = 100)
    private String email;
}

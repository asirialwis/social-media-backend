package org.chat.messagingweb.domain.entity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chat.messagingweb.domain.common.BaseEntity;
import org.chat.messagingweb.domain.enums.NotificationType;
import org.hibernate.type.NumericBooleanConverter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false)
    private User actor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @Builder.Default
    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;
}

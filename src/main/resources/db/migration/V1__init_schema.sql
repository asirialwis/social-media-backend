-- Users Table
CREATE TABLE users (
                       id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR2(50) NOT NULL UNIQUE,
                       email VARCHAR2(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- CREATE INDEX idx_users_username ON users(username);

-- Comments Table
CREATE TABLE comments (
                          id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          content CLOB NOT NULL,
                          author_id NUMBER(19) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Notifications Table
CREATE TABLE notifications (
                               id NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               recipient_id NUMBER(19) NOT NULL,
                               actor_id NUMBER(19) NOT NULL,
                               type VARCHAR2(50) NOT NULL,
                               reference_id NUMBER(19) NOT NULL,
                               is_read NUMBER(1) DEFAULT 0 NOT NULL, -- 0 for false, 1 for true
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                               CONSTRAINT fk_notif_recipient FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_notif_actor FOREIGN KEY (actor_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_notif_recipient_read ON notifications(recipient_id, is_read);
-- ====================================================================
-- SEED DATA FOR TESTING TAGGING & NOTIFICATIONS
-- ====================================================================

-- 1. Insert Initial Users
INSERT INTO users (username, email) VALUES ('alice_dev', 'alice@example.com');
INSERT INTO users (username, email) VALUES ('bob_builder', 'bob@example.com');
INSERT INTO users (username, email) VALUES ('charlie_lms', 'charlie@example.com');
INSERT INTO users (username, email) VALUES ('david_instructor', 'david@example.com');
INSERT INTO users (username, email) VALUES ('eva_student', 'eva@example.com');

-- 2. Insert Sample Comments
-- Alice posts a comment tagging Bob and Charlie
INSERT INTO comments (content, author_id)
VALUES ('Hey @bob_builder and @charlie_lms, please review the latest lecture materials.', 1);

-- Bob replies tagging Alice
INSERT INTO comments (content, author_id)
VALUES ('Thanks @alice_dev! I checked the documents and they look great.', 2);

-- David posts an announcement tagging Eva
INSERT INTO comments (content, author_id)
VALUES ('Welcome to the platform @eva_student. Let me know if you need help with enrollment.', 4);

-- 3. Insert Corresponding Seed Notifications
-- Notification for Bob (Recipient ID: 2, Actor ID: 1 - Alice)
INSERT INTO notifications (recipient_id, actor_id, type, reference_id, is_read)
VALUES (2, 1, 'TAGGED_IN_COMMENT', 1, 0);

-- Notification for Charlie (Recipient ID: 3, Actor ID: 1 - Alice)
INSERT INTO notifications (recipient_id, actor_id, type, reference_id, is_read)
VALUES (3, 1, 'TAGGED_IN_COMMENT', 1, 0);

-- Notification for Alice (Recipient ID: 1, Actor ID: 2 - Bob) - Already read
INSERT INTO notifications (recipient_id, actor_id, type, reference_id, is_read)
VALUES (1, 2, 'TAGGED_IN_COMMENT', 2, 1);

-- Notification for Eva (Recipient ID: 5, Actor ID: 4 - David)
INSERT INTO notifications (recipient_id, actor_id, type, reference_id, is_read)
VALUES (5, 4, 'TAGGED_IN_COMMENT', 3, 0);

COMMIT;
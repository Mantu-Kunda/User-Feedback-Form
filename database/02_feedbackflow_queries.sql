
USE feedbackflow_db;

-- 1. Insert sample categories
INSERT INTO categories (category_name)
VALUES ('Website'), ('Application'), ('Other')
ON DUPLICATE KEY UPDATE category_name = VALUES(category_name);

-- 2. View all categories
SELECT * FROM categories;

-- 3. View all feedback with submitter details
SELECT
    f.feedback_id,
    s.name,
    s.email,
    f.message,
    f.status,
    f.rating,
    f.created_at
FROM feedback AS f
JOIN submitters AS s
    ON f.submitter_id = s.submitter_id
ORDER BY f.created_at DESC;

-- 4. Count feedback by status
SELECT status, COUNT(*) AS total_feedback
FROM feedback
GROUP BY status;

-- 5. Find feedback that is not resolved
SELECT feedback_id, message, status
FROM feedback
WHERE status <> 'RESOLVED';

-- 6. Show average rating
SELECT AVG(rating) AS average_rating
FROM feedback
WHERE rating IS NOT NULL;

-- 7. Search feedback messages
SELECT feedback_id, message, status
FROM feedback
WHERE message LIKE '%service%';

-- 8. View the database report
SELECT * FROM v_feedback_report;

-- 9. View status change audit history
SELECT * FROM feedback_audit
ORDER BY changed_at DESC;

-- 10. Check indexes
SHOW INDEX FROM feedback;

-- 11. Check foreign-key relationships
SELECT
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'feedbackflow_db'
  AND REFERENCED_TABLE_NAME IS NOT NULL;


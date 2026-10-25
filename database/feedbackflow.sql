
CREATE DATABASE IF NOT EXISTS feedbackflow_db;
USE feedbackflow_db;

-- Check existing tables
SHOW TABLES;

-- View saved submitters
SELECT * FROM submitters;

-- View submitted feedback
SELECT * FROM feedback;

-- View audit records
SELECT * FROM feedback_audit;

-- View available categories
SELECT * FROM categories;


-- ==========================================
-- UserFeedbackApp: Database Documentation
-- ==========================================

USE feedbackflow_db;

-- 1. List all tables
SHOW TABLES;

-- 2. View report
SELECT * FROM v_feedback_report;

-- 3. Check audit trigger
SHOW TRIGGERS;

-- 4. Check feedback indexes
SHOW INDEX FROM feedback;

-- 5. Check foreign keys
SELECT
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'feedbackflow_db'
  AND REFERENCED_TABLE_NAME IS NOT NULL;

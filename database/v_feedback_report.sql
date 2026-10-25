USE feedbackflow_db;

CREATE OR REPLACE VIEW v_feedback_report AS
SELECT
    f.feedback_id,
    s.submitter_id,
    s.name,
    s.email,
    f.category_id,
    f.message,
    f.status,
    f.rating,
    f.created_at,
    f.updated_at
FROM feedback AS f
JOIN submitters AS s
    ON f.submitter_id = s.submitter_id
LEFT JOIN categories AS c
    ON f.category_id = c.category_id;


USE feedbackflow_db;

DROP TRIGGER IF EXISTS trg_feedback_status_audit;

DELIMITER $$

CREATE TRIGGER trg_feedback_status_audit
AFTER UPDATE ON feedback
FOR EACH ROW
BEGIN
    IF OLD.status <> NEW.status THEN
        INSERT INTO feedback_audit
            (feedback_id, old_status, new_status, changed_at, changed_by)
        VALUES
            (NEW.feedback_id, OLD.status, NEW.status,
             CURRENT_TIMESTAMP, CURRENT_USER());
    END IF;
END$$

DELIMITER ;


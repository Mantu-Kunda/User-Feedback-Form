User Feedback Management System

Advanced Internet Programming (AIP) + DBMS / Advanced DBMS

A Java web application that collects user feedback through a web interface and manages feedback records using MySQL. This project combines Advanced Internet Programming (AIP) concepts with Database Management Systems (DBMS) concepts in one application.

 1. Project Overview

The User Feedback Management System allows users to submit feedback through a web form. Java Servlets process the submitted data, while MySQL stores and manages the feedback records. Database features such as relational tables, SQL queries, views, and triggers support feedback management and status-change auditing.

 2. Objectives

- Develop a dynamic web application using Java Servlets.
- Understand HTTP GET and POST request handling.
- Connect a Java web application to MySQL using JDBC.
- Store and retrieve feedback using relational database tables.
- Apply SQL joins, aggregate queries, views, constraints, and triggers.
- Maintain an audit trail for feedback status changes.
- Practise web application deployment using Apache Tomcat.

 3. Features

 A. Advanced Internet Programming (AIP)

- Web-based feedback submission form.
- Java Servlet request processing.
- HTTP GET and POST handling.
- Dynamic response after form submission.
- Java-to-MySQL connectivity using JDBC.
- Deployment and testing using Apache Tomcat 11.

 B. DBMS / Advanced DBMS

- MySQL relational database.
- Structured tables for submitters, feedback, and categories.
- Primary keys, foreign keys, and constraints.
- SQL INSERT, SELECT, UPDATE, and filtering operations.
- JOIN queries to combine related records.
- Aggregate queries for feedback statistics.
- Database view for consolidated feedback reporting.
- Trigger-based auditing of feedback status changes.
- Feedback management through a dedicated servlet.

 4. Technology Stack

| Technology       | Purpose                            |
| ---------------- | ---------------------------------- |
| HTML/CSS         | User interface                     |
| Java             | Application logic                  |
| Jakarta Servlets | HTTP request handling              |
| JDBC             | Java–MySQL connectivity            |
| MySQL 9.7.2      | Relational database                |
| Apache Tomcat 11 | Servlet container                  |
| Eclipse IDE      | Development environment            |
| Git and GitHub   | Version control and source hosting |

 5. Project Structure

```text
UserFeedbackApp/
├── database/
│   ├── 01_feedbackflow_schema.sql
│   ├── 02_feedbackflow_queries.sql
│   ├── 03_feedback_audit_trigger.sql
│   ├── feedbackflow.sql
│   └── v_feedback_report.sql
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── feedback/
│       │           ├── DBConnection.java
│       │           ├── FeedbackServlet.java
│       │           └── FeedbackManagementServlet.java
│       └── webapp/
│           └── WEB-INF/
│               └── lib/
│                   └── mysql-connector-j-26.10.0.jar
└── README.md
```

*Note: This is a simplified view of the project structure. Other existing files may also be present.*

 6. Database Design

The application uses the `feedbackflow_db` database.

| Table            | Purpose                                       |
| ---------------- | --------------------------------------------- |
| `submitters`     | Stores submitter details                      |
| `categories`     | Stores feedback categories                    |
| `feedback`       | Stores feedback messages, ratings, and status |
| `feedback_audit` | Records feedback status changes               |

The database also includes a reporting view named `v_feedback_report` and a trigger named `trg_feedback_status_audit`.

 Database relationships

- A submitter can submit multiple feedback records.
- A feedback record can be associated with a category.
- Feedback status changes can be recorded in the audit table.

 7. AIP Concepts Demonstrated

 HTTP GET and POST

- **GET:** Used to request or retrieve a resource.
- **POST:** Used to submit form data for processing.

 Servlet processing

The `FeedbackServlet` handles feedback form requests and coordinates processing. The `FeedbackManagementServlet` is intended to display and manage feedback records.

 JDBC connectivity

The `DBConnection` class provides the database connection used by the Java application.

 8. DBMS Concepts Demonstrated

 SQL queries

The SQL scripts demonstrate database creation, table definitions, data retrieval, joins, filtering, grouping, and database metadata inspection.

 Views

The `v_feedback_report` view combines feedback information with related submitter and category information to support reporting.

 Triggers and audit logging

The `trg_feedback_status_audit` trigger is designed to record changes to feedback status in `feedback_audit`.

 Constraints and relationships

Primary keys, foreign keys, uniqueness rules, and validation constraints help maintain data integrity.

 9. Requirements

Install or configure the following before running the project:

- Java Development Kit compatible with the Eclipse project configuration.
- Eclipse IDE for Enterprise Java and Web Developers, or an equivalent setup.
- Apache Tomcat 11.
- MySQL Server.
- MySQL Connector/J.
- Git, if you want to clone the repository.

 10. Setup and Installation

 Step 1: Clone the repository

```bash
git clone https://github.com/Mantu-Kunda/User-Feedback-Form.git
cd User-Feedback-Form
```

 Step 2: Import into Eclipse

1. Open Eclipse.
2. Select **File → Import**.
3. Choose **General → Existing Projects into Workspace**, or the appropriate existing web project import option.
4. Select the cloned project directory.
5. Confirm the project is configured for Tomcat 11.

 Step 3: Configure MySQL

Create or select the database:

```sql
CREATE DATABASE IF NOT EXISTS feedbackflow_db;
USE feedbackflow_db;
```

Inspect the existing schema and tables before running any SQL script:

```sql
SHOW TABLES;
```

The `database/` directory contains schema, query, view, and trigger scripts for reference. Review their statements and compare them with your existing database before executing them. In particular, avoid dropping or recreating tables and triggers on a database that already contains data.

 Step 4: Configure database credentials

Configure your MySQL connection details in `DBConnection.java` or the corresponding environment-based configuration.

Set the database password through the environment variable expected by the application:

```bash
export FEEDBACK_DB_PASSWORD='your_mysql_password'
```

Replace the example value with your actual local password. Never commit real database passwords or access tokens to GitHub.

If you launch Eclipse from the desktop, ensure the environment variable is available to the Eclipse/Tomcat process.

 Step 5: Run the application

1. Start MySQL.
2. In Eclipse, right-click the project.
3. Select **Run As → Run on Server**.
4. Select Apache Tomcat 11.
5. Open the deployed application's URL in your browser.
6. Submit a test feedback entry and verify the result in MySQL.

The exact URL depends on the application's configured context path and servlet mappings.

 11. Testing Checklist

- [ ] Feedback form opens successfully.
- [ ] Feedback can be submitted.
- [ ] Java Servlet processes the request.
- [ ] Submitted feedback is stored in MySQL.
- [ ] Feedback records can be retrieved with SQL queries.
- [ ] Reporting view returns the expected results.
- [ ] Feedback management page loads.
- [ ] Updating feedback status works.
- [ ] Status changes appear in the audit table.

Only mark a test complete after verifying it in your environment.

 12. Learning Outcomes

This project provides practical experience with:

- Java web application development.
- Servlet lifecycle and HTTP request handling.
- JDBC connectivity and relational database operations.
- SQL queries and relational joins.
- Database constraints, views, and triggers.
- Database auditing and data integrity.
- Application deployment and Git-based collaboration.

 13. Future Enhancements

- User authentication and role-based access control.
- Pagination and search for feedback records.
- Improved validation and error handling.
- Dashboard charts for feedback analytics.
- Secure externalized configuration.
- Automated tests for servlet and database operations.

 14. Repository

**GitHub:** [https://github.com/Mantu-Kunda/User-Feedback-Form](https://github.com/Mantu-Kunda/User-Feedback-Form)

---

**Academic Project:** Advanced Internet Programming (AIP) and Database Management Systems (DBMS)
**Project:** User Feedback Management System

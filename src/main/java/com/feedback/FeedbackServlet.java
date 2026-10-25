
package com.feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        processFeedback(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        processFeedback(request, response, "POST");
    }

    private void processFeedback(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String method)
            throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("feedback");

        name = name == null ? "" : name.trim();
        email = email == null ? "" : email.trim();
        message = message == null ? "" : message.trim();

        response.setContentType("text/html;charset=UTF-8");

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(
                "<h2>Please provide your name, email, and feedback.</h2>"
                + "<a href='index.html'>Return to form</a>"
            );
            return;
        }

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            long submitterId;

            // Insert a new submitter or update the name
            // if this email address already exists.
            String submitterSql =
                "INSERT INTO submitters (name, email) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE name = VALUES(name)";

            try (PreparedStatement ps = connection.prepareStatement(
                    submitterSql)) {

                ps.setString(1, name);
                ps.setString(2, email);
                ps.executeUpdate();
            }

            // Find the submitter's database ID.
            String findSubmitterSql =
                "SELECT submitter_id FROM submitters WHERE email = ?";

            try (PreparedStatement ps = connection.prepareStatement(
                    findSubmitterSql)) {

                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Submitter was not found.");
                    }

                    submitterId = rs.getLong("submitter_id");
                }
            }

            // Save the feedback message.
            String feedbackSql =
                "INSERT INTO feedback "
                + "(submitter_id, category_id, message, status, rating) "
                + "VALUES (?, NULL, ?, 'NEW', NULL)";

            try (PreparedStatement ps = connection.prepareStatement(
                    feedbackSql)) {

                ps.setLong(1, submitterId);
                ps.setString(2, message);
                ps.executeUpdate();
            }

            // Both database operations succeeded.
            connection.commit();

            // Display confirmation only after the commit.
            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'>");
            out.println("<title>Feedback Confirmation</title>");
            out.println("<style>");
            out.println("body {font-family:Arial,sans-serif;"
                    + "background:linear-gradient(135deg,#667eea,#764ba2);"
                    + "display:flex;justify-content:center;"
                    + "align-items:center;min-height:100vh;margin:0;}");
            out.println(".card {background:white;padding:35px;"
                    + "border-radius:15px;width:420px;max-width:85%;"
                    + "box-shadow:0 10px 30px rgba(0,0,0,0.2);}");
            out.println("h2 {color:#28a745;text-align:center;}");
            out.println("p {color:#444;line-height:1.6;"
                    + "overflow-wrap:anywhere;}");
            out.println("a {display:block;text-align:center;"
                    + "background:#667eea;color:white;padding:12px;"
                    + "border-radius:7px;text-decoration:none;margin-top:25px;}");
            out.println("</style></head><body>");
            out.println("<div class='card'>");
            out.println("<h2>Feedback Submitted Successfully!</h2>");
            out.println("<p>Thank you for your feedback.</p>");
            out.println("<hr>");
            out.println("<p><strong>Name:</strong> "
                    + escapeHtml(name) + "</p>");
            out.println("<p><strong>Email:</strong> "
                    + escapeHtml(email) + "</p>");
            out.println("<p><strong>Feedback:</strong> "
                    + escapeHtml(message) + "</p>");
            out.println("<p><strong>Request Method:</strong> "
                    + escapeHtml(method) + "</p>");
            out.println("<a href='index.html'>Submit Another Feedback</a>");
            out.println("</div></body></html>");

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            getServletContext().log(
                "Failed to save submitted feedback.", e
            );

            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().println(
                "<!DOCTYPE html><html><head><meta charset='UTF-8'>"
                + "<title>Submission Error</title></head><body>"
                + "<h2>We could not save your feedback.</h2>"
                + "<p>Please try again later.</p>"
                + "<a href='index.html'>Return to form</a>"
                + "</body></html>"
            );

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getServletContext().log(
                        "Failed to close database connection.", e
                    );
                }
            }
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}

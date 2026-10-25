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

// Keep your existing package declaration above these imports.

@WebServlet("/manage-feedback")
public class FeedbackManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head>");
            out.println("<title>Feedback Management</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:30px;background:#f1f5f9;color:#172554}");
            out.println("table{width:100%;border-collapse:collapse;background:white}");
            out.println("th,td{padding:12px;border:1px solid #ddd;text-align:left}");
            out.println("th{background:#1d4ed8;color:white}");
            out.println("select,button{padding:7px;margin:3px}");
            out.println("</style></head><body>");
            out.println("<h1>Feedback Management</h1>");
            out.println("<table><tr><th>ID</th><th>Name</th>");
            out.println("<th>Email</th><th>Message</th><th>Status</th><th>Update</th></tr>");

            String sql =
                "SELECT f.feedback_id, s.name, s.email, f.message, f.status " +
                "FROM feedback f JOIN submitters s " +
                "ON f.submitter_id = s.submitter_id " +
                "ORDER BY f.created_at DESC";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("feedback_id");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + escape(rs.getString("name")) + "</td>");
                    out.println("<td>" + escape(rs.getString("email")) + "</td>");
                    out.println("<td>" + escape(rs.getString("message")) + "</td>");
                    out.println("<td>" + escape(rs.getString("status")) + "</td>");
                    out.println("<td><form method='post'>");
                    out.println("<input type='hidden' name='feedback_id' value='" + id + "'>");
                    out.println("<select name='status'>");

                    String current = rs.getString("status");
                    for (String status : new String[]{"NEW", "IN_PROGRESS", "RESOLVED"}) {
                        out.println("<option value='" + status + "'"
                            + (status.equals(current) ? " selected" : "")
                            + ">" + status + "</option>");
                    }

                    out.println("</select><button type='submit'>Update</button>");
                    out.println("</form></td></tr>");
                }

            } catch (SQLException e) {
                getServletContext().log("Could not load feedback", e);
                out.println("<tr><td colspan='6'>Unable to load feedback. Check the server log.</td></tr>");
            }

            out.println("</table></body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String idText = request.getParameter("feedback_id");
        String status = request.getParameter("status");

        if (idText == null ||
            !idText.matches("\\d+") ||
            !("NEW".equals(status) ||
              "IN_PROGRESS".equals(status) ||
              "RESOLVED".equals(status))) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "Invalid feedback ID or status.");
            return;
        }

        String sql = "UPDATE feedback SET status = ? WHERE feedback_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, Long.parseLong(idText));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Could not update feedback status.", e);
        }

        response.sendRedirect(
            request.getContextPath() + "/manage-feedback");
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}

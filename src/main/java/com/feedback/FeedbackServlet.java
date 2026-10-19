
package com.feedback;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Handles GET Request
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        processFeedback(request, response, "GET");
    }

    // Handles POST Request
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        processFeedback(request, response, "POST");
    }

    // Process feedback submitted by the user
    private void processFeedback(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String method)
            throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String feedback = request.getParameter("feedback");

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Feedback Confirmation</title>");

        // CSS Styling
        out.println("<style>");

        out.println("body {");
        out.println("font-family: Arial, sans-serif;");
        out.println("background: linear-gradient(135deg,#667eea,#764ba2);");
        out.println("display:flex;");
        out.println("justify-content:center;");
        out.println("align-items:center;");
        out.println("min-height:100vh;");
        out.println("margin:0;");
        out.println("}");

        out.println(".card {");
        out.println("background:white;");
        out.println("padding:35px;");
        out.println("border-radius:15px;");
        out.println("width:420px;");
        out.println("max-width:85%;");
        out.println("box-shadow:0 10px 30px rgba(0,0,0,0.2);");
        out.println("}");

        out.println("h2 {color:#28a745;text-align:center;}");
        out.println("p {color:#444;line-height:1.6;overflow-wrap:anywhere;}");
        out.println("strong {color:#222;}");

        out.println("a {");
        out.println("display:block;");
        out.println("text-align:center;");
        out.println("background:#667eea;");
        out.println("color:white;");
        out.println("padding:12px;");
        out.println("border-radius:7px;");
        out.println("text-decoration:none;");
        out.println("margin-top:25px;");
        out.println("}");

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<div class='card'>");

        out.println("<h2>Feedback Submitted Successfully!</h2>");

        out.println("<p>Thank you for your feedback.</p>");
        out.println("<hr>");

        out.println("<p><strong>Name:</strong> "
                + escapeHtml(name) + "</p>");

        out.println("<p><strong>Email:</strong> "
                + escapeHtml(email) + "</p>");

        out.println("<p><strong>Feedback:</strong> "
                + escapeHtml(feedback) + "</p>");

        out.println("<p><strong>Request Method:</strong> "
                + method + "</p>");

        out.println("<a href='index.html'>Submit Another Feedback</a>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    // Prevent submitted text from being interpreted as HTML
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

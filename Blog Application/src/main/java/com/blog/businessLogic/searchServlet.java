package com.blog.businessLogic;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/searchServlet")
public class searchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "root");
            String sql = "SELECT * FROM posts WHERE title LIKE ? ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            while (rs.next()) {
                response.getWriter().println("<h3>" + rs.getString("title") + "</h3>");
                response.getWriter().println("<p>" + rs.getString("content") + "</p>");
                if (rs.getString("media") != null) {
                    response.getWriter().println("<img src='uploadFiles/" + rs.getString("media") + "' width='300'>");
                }
            }
            response.getWriter().println("</body></html>");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

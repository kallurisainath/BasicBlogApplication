package com.blog.businessLogic;
import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        String role = request.getParameter("role");

	        // Password hashing (use a library like BCrypt)
//	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        }
	        catch (Exception e) {
	        	System.out.println("Class not found");
			}
	        try {
	            
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "root");
	            String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
	            PreparedStatement pstmt = con.prepareStatement(query);
	            pstmt.setString(1, name);
	            pstmt.setString(2, email);
	            pstmt.setString(3, password);
	            pstmt.setString(4, role);
	            pstmt.executeUpdate();
	            con.close();
	            response.sendRedirect("login.jsp");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("Error: " + e.getMessage());
	        }
	    }

}

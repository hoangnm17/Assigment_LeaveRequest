/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import models.User;

/**
 *
 * @author 84911
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//        UserDBContext db = new UserDBContext();
//        User user = db.get(username, password);
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        
        if (user.getUserName() != username) {
            HttpSession session = request.getSession();
            session.setAttribute("auth", user);
            request.setAttribute("message", "login successful!");
            response.sendRedirect("view/RoleType/Admin/dashboard.jsp");
        } else {
            request.setAttribute("message", "login failed!");
        }
        

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
    }

}

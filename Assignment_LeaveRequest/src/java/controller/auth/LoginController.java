/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import dal.RoleDBContext;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import models.User;

/**
 *
 * @author 84911
 */
@WebServlet(name = "LoginController", urlPatterns = {"/auth/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDBContext userDAO = new UserDBContext();
        User account = userDAO.getUser(username, password);

        if (account != null) {
            RoleDBContext roleDAO = new RoleDBContext();
            List<String> permissions = roleDAO.getRole(account.getId());
            System.out.println("----- QUYEN CUA USER " + account.getUserName()
                    + " (UserID thuc te la: " + account.getId() + "): " + permissions);
            HttpSession session = request.getSession();
            session.setAttribute("auth", account);
            session.setAttribute("permissions", permissions);

            response.sendRedirect(request.getContextPath() + "/view/dashboard.jsp");
        } else {
            request.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác!");
            request.getRequestDispatcher("../view/auth/login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../view/auth/login.jsp").forward(request, response);
    }

}

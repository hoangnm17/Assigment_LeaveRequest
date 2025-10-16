package controller.create_request;

import controller.auth.BaseRequiredAuthenticationController;
import dal.LeaveRequestDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import models.LeaveRequest;
import models.LeaveType;
import models.User;

/**
 *
 * @author 84911
 */
@WebServlet(name = "CreateRequestController", urlPatterns = {"/app/create"})
public class CreateRequestController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        request.getRequestDispatcher("../view/leaveRequest/requestApp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        LeaveRequest leave_req = new LeaveRequest();
        LeaveType leave_type = new LeaveType();
        int leave_type_raw = Integer.parseInt(request.getParameter("leaveType"));
        leave_type.setId(leave_type_raw);

        leave_req.setUser(user);
        leave_req.setLeaveType(leave_type);
        leave_req.setStartDate(Date.valueOf(request.getParameter("startDate")));
        leave_req.setEndDate(Date.valueOf(request.getParameter("endDate")));
        leave_req.setReason(request.getParameter("reason"));

        LeaveRequestDBContext leave_req_db = new LeaveRequestDBContext();
        leave_req_db.insert(leave_req);
        request.setAttribute("message", "Sended applies!");
        request.getRequestDispatcher("../view/leaveRequest/requestApp.jsp").forward(request, response);
    }

}

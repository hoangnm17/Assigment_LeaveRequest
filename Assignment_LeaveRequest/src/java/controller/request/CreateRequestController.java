package controller.request;

import controller.auth.BaseRequiredAuthorizedController;
import dal.ApprovalStepDAO;
import dal.LeaveRequestDAO;
import dal.LeaveTypeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import model.leave.ApprovalStep;
import model.leave.LeaveRequest;
import model.leave.LeaveType;
import model.user.User;
import utils.ConfigLoader;

@WebServlet(name = "CreateRequestController", urlPatterns = {"/request/create"})
public class CreateRequestController extends BaseRequiredAuthorizedController {


    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.create");
    }

    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        // Phần này đã đúng
        LeaveTypeDAO leaveTypeDAO = new LeaveTypeDAO();
        request.setAttribute("leaveTypes", leaveTypeDAO.getAllLeaveTypes());
        request.getRequestDispatcher("/view/request/request-leave.jsp").forward(request, response);
    }

    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        try {
            LeaveRequest leave_req = new LeaveRequest();

            LeaveType leave_type = new LeaveType();
            int leave_type_raw = Integer.parseInt(request.getParameter("leaveType"));
            leave_type.setId(leave_type_raw);

            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            leave_req.setCreated_by(user.getEmployee());
            leave_req.setLeaveType(leave_type);
            leave_req.setStartDate(startDate);
            leave_req.setEndDate(endDate);
            leave_req.setReason(request.getParameter("reason"));
            leave_req.setStatus("In progress");
            leave_req.setCreated_time(new Timestamp(System.currentTimeMillis()));

            LeaveRequestDAO leave_req_db = new LeaveRequestDAO();
            leave_req_db.insert(leave_req);
            ApprovalStep app_step_raw = new ApprovalStep();
            app_step_raw.setRequest(leave_req);
            app_step_raw.setStatus("In progress");
            
            ApprovalStepDAO stepDAO = new ApprovalStepDAO();
            stepDAO.insert(app_step_raw);
            request.setAttribute("message", "Đơn xin nghỉ đã được gửi thành công!");
            request.getRequestDispatcher("/view/request/request-leave.jsp").forward(request, response);

        } catch (Exception e) {
            // Khối catch này đã đúng
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra, không thể gửi đơn: " + e.getMessage());
            processGetAuthorized(request, response, user); // Tải lại trang form
        }
    }

}

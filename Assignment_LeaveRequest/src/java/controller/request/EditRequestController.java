package controller.request;

import controller.auth.BaseRequiredAuthorizedController;
import dal.LeaveRequestDAO;
import dal.LeaveTypeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.leave.LeaveRequest;
import model.leave.LeaveType;
import model.user.User;
import utils.ConfigLoader;
import utils.LeaveRequestValidator;

@WebServlet(name = "EditRequestController", urlPatterns = {"/request/edit"})
public class EditRequestController extends BaseRequiredAuthorizedController {

    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.edit");
    }

    /**
     * Hiển thị form chỉnh sửa đơn
     */
    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("id"));
        LeaveRequestDAO lrDAO = new LeaveRequestDAO();
        LeaveRequest lr = lrDAO.getByRequestId(requestId);

        // Kiểm tra: chỉ được sửa đơn của chính mình + đang In Progress
        if (lr == null
                || lr.getCreated_by() == null
                || lr.getCreated_by().getId() != user.getId()
                || !"In Progress".equalsIgnoreCase(lr.getStatus())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Bạn không có quyền chỉnh sửa đơn này hoặc đơn đã được xử lý.");
            return;
        }

        // Truyền dữ liệu sang JSP
        request.setAttribute("leave", lr);
        LeaveTypeDAO leaveTypeDAO = new LeaveTypeDAO();
        request.setAttribute("leaveTypes", leaveTypeDAO.getAllLeaveTypes());
        request.getRequestDispatcher("/view/request/edit-request.jsp").forward(request, response);
    }

    /**
     * Xử lý lưu lại thay đổi
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        int requestId = Integer.parseInt(request.getParameter("id"));
        LeaveRequestDAO lrDAO = new LeaveRequestDAO();
        LeaveRequest lr = lrDAO.get(requestId);

        // Kiểm tra quyền sở hữu & trạng thái
        if (lr == null
                || lr.getCreated_by() == null
                || lr.getCreated_by().getId() != user.getId()
                || !"In Progress".equalsIgnoreCase(lr.getStatus())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Bạn không có quyền chỉnh sửa đơn này hoặc đơn đã được xử lý.");
            return;
        }

        try {
            // Khởi tạo validator
            LeaveRequestValidator validator = new LeaveRequestValidator(
                    request.getParameter("typeId"),
                    request.getParameter("fromDate"),
                    request.getParameter("toDate"),
                    request.getParameter("reason")
            );

            // Kiểm tra lỗi
            List<String> errors = validator.validate();
            if (!errors.isEmpty()) {
                request.setAttribute("error", String.join(" ", errors));
                processGetAuthorized(request, response, user); // tải lại form với dữ liệu cũ
                return;
            }

            // Cập nhật thông tin
            lr.setReason(validator.getReason());
            lr.setStartDate(validator.getStartDate());
            lr.setEndDate(validator.getEndDate());

            LeaveType leaveType = new LeaveType();
            leaveType.setId(validator.getLeaveTypeId());
            lr.setLeaveType(leaveType);

            lrDAO.update(lr);

            // Quay lại danh sách đơn
            response.sendRedirect(request.getContextPath() + "/request/list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lưu đơn: " + e.getMessage());
            processGetAuthorized(request, response, user);
        }
    }
}

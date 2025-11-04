package controller.approval;

import controller.auth.BaseRequiredAuthorizedController;
import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.leave.LeaveRequest;
import model.user.User;
import utils.ConfigLoader;

/**
 * Controller hiển thị danh sách các đơn cần phê duyệt
 * Chỉ những người có quyền "approval:view" mới được truy cập
 */
@WebServlet(name = "ApprovalController", urlPatterns = {"/approval/list"})
public class ApprovalController extends BaseRequiredAuthorizedController {

    /**
     * SỬA 1: BẮT BUỘC CÓ - Khai báo quyền
     * (Lấy từ comment của bạn)
     */
    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.view.all");
    }

    /**
     * SỬA 2: Đổi tên thành 'processGetAuthorized'
     */
    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        // Logic gốc của bạn đã đúng
        LeaveRequestDAO appDAO = new LeaveRequestDAO();
        ArrayList<LeaveRequest> approval_list = appDAO.getRequestsByManagerHierarchy(user.getId());
        request.setAttribute("list_approval", approval_list);
        
        // Kiểm tra thông báo thành công từ trang Create/Update
        if ("true".equals(request.getParameter("create_success"))) {
            request.setAttribute("message", "Tạo đơn thành công!");
        }
        if ("true".equals(request.getParameter("update_success"))) {
            request.setAttribute("message", "Cập nhật đơn thành công!");
        }
        
        request.getRequestDispatcher("/view/approval/approval-list.jsp").forward(request, response);
    }

    /**
     * SỬA 3: Đổi tên thành 'processPostAuthorized'
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        // Logic gốc của bạn đã đúng
        processGetAuthorized(request, response, user);
    }
}
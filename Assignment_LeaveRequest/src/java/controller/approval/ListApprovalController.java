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
 * Controller hiển thị danh sách các đơn cần phê duyệt Chỉ những người có quyền
 * "leave.view.all" mới được truy cập
 */
@WebServlet(name = "ListApprovalController", urlPatterns = {"/approval/list"})
public class ListApprovalController extends BaseRequiredAuthorizedController {

    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.view.all");
    }

    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        int pageIndex = 1;
        int pageSize = 10;

        try {
            String configPageSize = ConfigLoader.get("page.size");
            if (configPageSize != null) {
                pageSize = Integer.parseInt(configPageSize);
            }
        } catch (NumberFormatException e) {
            // fallback an toàn
            pageSize = 10;
        }

        // 1. Lấy pageIndex từ parameter
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                pageIndex = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            pageIndex = 1; // Mặc định là 1 nếu parse lỗi
        }

        // 2. Sửa lỗi 1: Đảm bảo pageIndex luôn là số dương (lớn hơn 0)
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();

        // 3. Lấy tổng số bản ghi TRƯỚC
        int totalRecords = dao.countRequestsByManagerHierarchy(user.getId());
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 4. Sửa lỗi 3: Xử lý trường hợp không có bản ghi nào
        // Luôn đảm bảo có ít nhất 1 trang (kể cả đó là trang rỗng)
        if (totalPages == 0) {
            totalPages = 1;
        }

        // 5. Sửa lỗi 2: Xử lý trường hợp pageIndex vượt quá tổng số trang
        // Nếu người dùng yêu cầu trang 10 mà chỉ có 5 trang, tự động đặt về trang 5
        if (pageIndex > totalPages) {
            pageIndex = totalPages;
        }

        // 6. Lấy danh sách (chỉ sau khi đã chuẩn hóa pageIndex)
        ArrayList<LeaveRequest> approvalList = dao.getRequestsByManagerHierarchy(user.getId(), pageIndex, pageSize);

        // 7. Set thuộc tính cho JSP
        request.setAttribute("list_approval", approvalList);
        request.setAttribute("currentPage", pageIndex); // Dùng pageIndex đã được chuẩn hóa
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/view/approval/approval-list.jsp").forward(request, response);
    }

    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        processGetAuthorized(request, response, user);
    }
}

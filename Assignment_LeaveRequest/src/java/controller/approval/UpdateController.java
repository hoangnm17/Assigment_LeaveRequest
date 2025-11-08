package controller.approval;

import controller.auth.BaseRequiredAuthorizedController;
import dal.ApprovalStepDAO;
import dal.LeaveRequestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.leave.LeaveRequest;
import model.user.User;
import utils.ConfigLoader;

@WebServlet(name = "UpdateController", urlPatterns = {"/approval/update"})
public class UpdateController extends BaseRequiredAuthorizedController {

    /**
     * SỬA 1: BẮT BUỘC CÓ - Khai báo quyền
     * (Bạn cần điền tên quyền của mình ở đây, ví dụ "leave:approve")
     */
    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.approve");
    }

    /**
     * SỬA 2: Đổi tên 'doGet' thành 'processGetAuthorized'
     * Logic forward GỐC của bạn được giữ nguyên.
     */
    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        // GIỮ NGUYÊN LOGIC GỐC CỦA BẠN
        request.getRequestDispatcher("/view/approval/approval-detail.jsp").forward(request, response);
    }

    /**
     * SỬA 3: Đổi tên 'doPost' thành 'processPostAuthorized'
     * Logic update 2 bảng GỐC của bạn được giữ nguyên.
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // GIỮ NGUYÊN LOGIC GỐC CỦA BẠN
        try {
            // Lấy dữ liệu từ form
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String action = request.getParameter("action"); // "approve" hoặc "reject"
            String note = request.getParameter("notes");

            // Khởi tạo DAO
            ApprovalStepDAO stepDAO = new ApprovalStepDAO();
            LeaveRequestDAO leaveDAO = new LeaveRequestDAO();

            // Xác định trạng thái dựa theo action
            String Status = action.equalsIgnoreCase("approve") ? "Approved" : "Rejected";

            // 1️⃣ Cập nhật bảng ApprovalStep
            stepDAO.updateStepStatus(requestId, user.getId(), Status, note);

            // 2️⃣ Cập nhật bảng LeaveRequest
            LeaveRequest lr = new LeaveRequest();
            lr.setId(requestId);
            lr.setStatus(Status);
            leaveDAO.updateStatus(lr);

            // 3️⃣ Thông báo kết quả và chuyển hướng
            response.sendRedirect(request.getContextPath() + "/approval/list");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request ID không hợp lệ!");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi khi cập nhật đơn nghỉ!");
        }
    }

    // SỬA 4: Xóa 2 phương thức thừa (processGet và processPost)
    // (Vì chúng không cần thiết và gây lỗi biên dịch)
}
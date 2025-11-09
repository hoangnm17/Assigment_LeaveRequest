package controller.approval;

import controller.auth.BaseRequiredAuthorizedController;
import dal.ApprovalStepDAO;
import dal.LeaveRequestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.leave.LeaveRequest;
import model.user.User;
import utils.ConfigLoader;

@WebServlet(name = "UpdateController", urlPatterns = {"/approval/update"})
public class UpdateController extends BaseRequiredAuthorizedController {

    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("approve.update");
    }

    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("id"));

        ArrayList<Integer> validIds = (ArrayList<Integer>) request.getSession().getAttribute("list_approval_ids");

        if (validIds == null || !validIds.contains(requestId)) {
            // Nếu người dùng truy cập trái phép
            request.getSession().setAttribute("approval_error", "Bạn không được phép truy cập đơn này!");
            response.sendRedirect(request.getContextPath() + "/approval/list");
            return;
        }

        // Cho phép xem chi tiết đơn
        request.getRequestDispatcher("/view/approval/approval-detail.jsp").forward(request, response);
    }

    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        try {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String action = request.getParameter("action"); // "approve" hoặc "reject"
            String note = request.getParameter("notes");

            // DAO
            ApprovalStepDAO stepDAO = new ApprovalStepDAO();
            LeaveRequestDAO leaveDAO = new LeaveRequestDAO();

            LeaveRequest lr = leaveDAO.getByRequestId(requestId);
            if (lr == null) {
                request.getSession().setAttribute("approval_error", "Không tìm thấy đơn nghỉ!");
                response.sendRedirect(request.getContextPath() + "/approval/list");
                return;
            }

            // Trạng thái mới
            String Status = action.equalsIgnoreCase("approve") ? "Approved" : "Rejected";

            // Cập nhật 2 bảng
            stepDAO.updateStepStatus(requestId, user.getId(), Status, note);
            lr.setStatus(Status);
            leaveDAO.updateStatus(lr);

            // ✅ Gửi thông báo thành công
            String msg = action.equalsIgnoreCase("approve")
                    ? "✅ Đơn nghỉ #" + requestId + " đã được phê duyệt thành công!"
                    : "❌ Đơn nghỉ #" + requestId + " đã bị từ chối!";
            request.getSession().setAttribute("approval_message", msg);

            // Quay lại danh sách duyệt
            response.sendRedirect(request.getContextPath() + "/approval/list");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("approval_error", "Mã đơn không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/approval/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("approval_error", "Đã xảy ra lỗi khi cập nhật đơn nghỉ!");
            response.sendRedirect(request.getContextPath() + "/approval/list");
        }
    }
}

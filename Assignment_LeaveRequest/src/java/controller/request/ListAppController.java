package controller.request;

// SỬA 1: Đổi lớp kế thừa thành "Authorized"
import controller.auth.BaseRequiredAuthorizedController;
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

@WebServlet(name = "ListAppController", urlPatterns = {"/request/list"})
// SỬA 2: Đổi lớp kế thừa thành "Authorized"
public class ListAppController extends BaseRequiredAuthorizedController {

    /**
     * SỬA 3: BẮT BUỘC CÓ - Khai báo quyền
     * (Giả sử quyền để xem danh sách của mình là "leave:view:own")
     */
    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("leave.view.all");
    }

    /**
     * SỬA 4: Đổi tên thành 'processGetAuthorized'
     */
    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // Logic lọc (filter) GỐC của bạn đã đúng
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all"; // mặc định là hiển thị tất cả
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        ArrayList<LeaveRequest> list;

        // Giả sử DAO của bạn dùng user.getEmployee().getId()
        int employeeId = user.getEmployee().getId(); 

        if ("all".equalsIgnoreCase(status)) {
            list = dao.getRequestsByEmployee(employeeId, null); // null -> lấy tất cả
        } else if ("approved".equalsIgnoreCase(status)) {
            list = dao.getRequestsByEmployee(employeeId, "Approved");
        } else if ("rejected".equalsIgnoreCase(status)) {
            list = dao.getRequestsByEmployee(employeeId, "Rejected");
        } else if ("pending".equalsIgnoreCase(status)) {
            list = dao.getRequestsByEmployee(employeeId, "In progress");
        } else {
            list = dao.getRequestsByEmployee(employeeId, null);
        }

        request.setAttribute("listApp", list);
        request.setAttribute("status", status); // Giữ lại status đã chọn cho JSP

        // Xử lý thông báo (từ trang Create)
        if ("true".equals(request.getParameter("create_success"))) {
            request.setAttribute("message", "Đơn xin nghỉ đã được gửi thành công!");
        }

        request.getRequestDispatcher("/view/request/list-request.jsp").forward(request, response);
    }

    /**
     * SỬA 5: Đổi tên thành 'processPostAuthorized'
     * (Và chuyển sang gọi processGetAuthorized)
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        // Nếu trang này có form filter dùng POST, thì gọi lại GET là hợp lý
        processGetAuthorized(request, response, user);
    }
    
    // SỬA 6: Xóa phương thức 'processRequest' riêng tư không dùng đến
}
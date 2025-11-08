package controller.request;

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

@WebServlet(name = "ListRequestController", urlPatterns = {"/request/list"})
public class ListRequestController extends BaseRequiredAuthorizedController {

    @Override
    protected String getRequiredPermission() {
        // Có thể đổi tùy quyền user: leave:view:own / leave:view:all
        return ConfigLoader.get("leave.view.all");
    }

    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // --- 1️⃣ Lấy và chuẩn hóa tham số status ---
        String statusParam = request.getParameter("status");
        String statusForDAO = null; // Giá trị status sẽ truyền vào DAO (null = "all")
        String statusForJSP = "inprogress"; // Giá trị status để hiển thị lại trên JSP

        if (statusParam != null && !statusParam.isEmpty()) {
            statusForJSP = statusParam.toLowerCase();
        }

        // Ánh xạ status từ JSP sang giá trị trong DB
        switch (statusForJSP) {
            case "approved":
                statusForDAO = "Approved";
                break;
            case "rejected":
                statusForDAO = "Rejected";
                break;
            case "inprogress":
                statusForDAO = "In progress";
                break;
            default:
                statusForDAO = null; // "all"
                statusForJSP = "all"; // Đảm bảo "all" là giá trị mặc định chuẩn
                break;
        }

        // --- 2️⃣ Lấy thông tin phân trang ---
        int page = 1;
        int pageSize = 10; // fallback mặc định
        try {
            String configPageSize = ConfigLoader.get("page.size");
            if (configPageSize != null) {
                pageSize = Integer.parseInt(configPageSize);
            }
        } catch (NumberFormatException e) {
            pageSize = 10; // fallback an toàn
        }

        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        // --- 3️⃣ Lấy tổng số bản ghi TRƯỚC ---
        LeaveRequestDAO dao = new LeaveRequestDAO();
        int employeeId = user.getEmployee().getId();

        // Chỉ gọi ĐẾM (count) trước
        int totalRecords = dao.countRequestsByEmployee(employeeId, statusForDAO);

        // --- 4️⃣ Tính toán và chuẩn hóa phân trang ---
        // 4a. Sửa lỗi 1: Đảm bảo pageIndex luôn là số dương (lớn hơn 0)
        if (page < 1) {
            page = 1;
        }

        // 4b. Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 4c. Sửa lỗi 3: Xử lý trường hợp không có bản ghi nào
        // Luôn đảm bảo có ít nhất 1 trang (kể cả đó là trang rỗng)
        if (totalPages == 0) {
            totalPages = 1;
        }

        // 4d. Sửa lỗi 2: Xử lý trường hợp pageIndex vượt quá tổng số trang
        // Nếu người dùng yêu cầu trang 10 mà chỉ có 5 trang, tự động đặt về trang 5
        if (page > totalPages) {
            page = totalPages;
        }

        // --- 5️⃣ Lấy danh sách (SAU KHI đã chuẩn hóa page) ---
        // Bây giờ mới gọi 'get' với số 'page' đã được chuẩn hóa
        ArrayList<LeaveRequest> list = dao.getRequestsByEmployee(employeeId, statusForDAO, page, pageSize);

        // --- 6️⃣ Gửi dữ liệu sang JSP ---
        request.setAttribute("listApp", list);
        request.setAttribute("status", statusForJSP); // Gửi status đã chuẩn hóa (all, pending,...)
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("paginationURL", "/request/list");
        request.getRequestDispatcher("/view/request/list-request.jsp").forward(request, response);
    }

    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        processGetAuthorized(request, response, user);
    }
}

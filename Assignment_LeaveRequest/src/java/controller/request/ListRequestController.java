package controller.request;

import controller.auth.BaseRequiredAuthorizedController;
import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map; // Đảm bảo đã import Map
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
        int totalRecords = dao.countRequestsByEmployee(employeeId, statusForDAO);

        // --- 4️⃣ Tính toán và chuẩn hóa phân trang ---
        if (page < 1) {
            page = 1;
        }
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        // --- 5️⃣ Lấy danh sách (SAU KHI đã chuẩn hóa page) ---
        ArrayList<LeaveRequest> list = dao.getRequestsByEmployee(employeeId, statusForDAO, page, pageSize);

        
        // --- (PHẦN MỚI) XÂY DỰNG QUERY STRING CHO PHÂN TRANG ---
        // Xây dựng một chuỗi query chứa tất cả filter, NGOẠI TRỪ 'page'
        StringBuilder queryStringForPagination = new StringBuilder();
        
        // Lặp qua tất cả tham số trên URL (ví dụ: status=all, search=abc)
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String paramName = entry.getKey();
            
            // Chỉ lấy các tham số KHÁC 'page'
            if (!paramName.equalsIgnoreCase("page")) { 
                for (String paramValue : entry.getValue()) {
                    // Nếu đã có tham số, thêm dấu &
                    if (queryStringForPagination.length() > 0) {
                        queryStringForPagination.append("&");
                    }
                    // Thêm cặp key=value đã được encode
                    queryStringForPagination.append(URLEncoder.encode(paramName, StandardCharsets.UTF_8.name()));
                    queryStringForPagination.append("=");
                    queryStringForPagination.append(URLEncoder.encode(paramValue, StandardCharsets.UTF_8.name()));
                }
            }
        }


        // --- 6️⃣ Gửi dữ liệu sang JSP ---
        request.setAttribute("listApp", list);
        request.setAttribute("status", statusForJSP); // Gửi status đã chuẩn hóa (all, pending,...)
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        // URL gốc của Controller (ví dụ: /request/list)
        request.setAttribute("paginationURL", "/request/list"); 
        
        // (MỚI) Gửi chuỗi filter đã build (ví dụ: "status=all")
        System.out.println(queryStringForPagination);
        request.setAttribute("paginationQuery", queryStringForPagination.toString()); 

        request.getRequestDispatcher("/view/request/list-request.jsp").forward(request, response);
    }

    /**
     * Xử lý filter (POST) bằng cách áp dụng Post-Redirect-Get (PRG)
     * (Phần này sẽ chạy nếu form của bạn dùng method="POST")
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        String status = request.getParameter("status");
        String redirectURL = request.getContextPath() + "/request/list";
        StringBuilder queryParams = new StringBuilder();

        if (status != null && !status.isEmpty()) {
            queryParams.append("status=")
                       .append(URLEncoder.encode(status, StandardCharsets.UTF_8.name()));
        }
        
        // (Mở rộng cho các filter khác nếu cần)

        if (queryParams.length() > 0) {
            redirectURL += "?" + queryParams.toString();
        }

        response.sendRedirect(redirectURL);
    }
}
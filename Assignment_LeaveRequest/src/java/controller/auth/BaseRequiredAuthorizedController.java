package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.user.User; // Đảm bảo bạn import đúng model User

public abstract class BaseRequiredAuthorizedController extends BaseRequiredAuthenticationController {

    /**
     * Kiểm tra xem user có quyền được yêu cầu hay không.
     */
    private boolean isAuthorized(HttpServletRequest request, User user) {
        // Lấy quyền mà LỚP CON định nghĩa
        String requiredPermission = getRequiredPermission();
        
        // Nếu lớp con không yêu cầu quyền đặc biệt, cho qua
        if (requiredPermission == null || requiredPermission.trim().isEmpty()) {
            return true;
        }

        // Lấy danh sách quyền của user từ session
        @SuppressWarnings("unchecked")
        List<String> userPermissions = (List<String>) request.getSession().getAttribute("permissions");
        if (userPermissions == null) {
            return false; // User không có quyền nào
        }

        // Kiểm tra xem user có quyền được yêu cầu không
        return userPermissions.contains(requiredPermission);
    }

    /**
     * Lớp con BẮT BUỘC phải override phương thức này
     * để khai báo quyền (permission) mà nó yêu cầu.
     * @return String của quyền (ví dụ: "leave:create") hoặc null nếu không cần.
     */
    protected abstract String getRequiredPermission();
    
    
    // --- CÁC HOOK MỚI ---
    
    /**
     * Hook mới: Lớp con cuối cùng sẽ override cái này
     * (Chỉ chạy khi đã ĐĂNG NHẬP và CÓ QUYỀN)
     */
    protected abstract void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException;

    /**
     * Hook mới: Lớp con cuối cùng sẽ override cái này
     * (Chỉ chạy khi đã ĐĂNG NHẬP và CÓ QUYỀN)
     */
    protected abstract void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException;

            
    // --- KẾT NỐI CHUỖI KẾ THỪA ---

    /**
     * Override hook của LỚP CHA (BaseRequiredAuthenticationController)
     * Phương thức này chỉ chạy KHI ĐÃ ĐĂNG NHẬP.
     * Nhiệm vụ của nó là kiểm tra PHÂN QUYỀN.
     */
    @Override
    protected final void processGet(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        if (isAuthorized(request, user)) {
            // Nếu có quyền, gọi hook cuối cùng
            processGetAuthorized(request, response, user);
        } else {
            // Không có quyền
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
        }
    }

    /**
     * Override hook của LỚP CHA (BaseRequiredAuthenticationController)
     * Tương tự cho POST.
     */
    @Override
    protected final void processPost(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        if (isAuthorized(request, user)) {
            // Nếu có quyền, gọi hook cuối cùng
            processPostAuthorized(request, response, user);
        } else {
            // Không có quyền
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
        }
    }
}
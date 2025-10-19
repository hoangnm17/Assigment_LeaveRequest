package filters;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    private boolean isPublicResource(String path) {
        return path.startsWith("/auth/") ||
               path.startsWith("/view/auth/") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/") ||
               path.startsWith("/assets/") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".png") ||
               path.endsWith(".jpg") ||
               path.endsWith(".jpeg") ||
               path.endsWith(".gif") ||
               path.endsWith(".ico") ||
               path.endsWith(".woff") ||
               path.endsWith(".ttf");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        // === 1. TÀI NGUYÊN CÔNG KHAI ===
        if (isPublicResource(servletPath)) {
            chain.doFilter(req, res);
            return;
        }

        // === 2. KIỂM TRA ĐĂNG NHẬP ===
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect(contextPath + "/auth/login");
            return;
        }

        // === 3. KIỂM TRA PHÂN QUYỀN ===
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) session.getAttribute("permissions");

        if (permissions == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Lỗi phân quyền.");
            return;
        }

        if (servletPath.equals("/request/create")) {
            if (!permissions.contains("leave:create")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền tạo đơn.");
                return;
            }
        } 
        else if (servletPath.equals("/request/approve")) {
            if (!permissions.contains("leave:approve:team") && !permissions.contains("leave:approve:dept")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền duyệt đơn.");
                return;
            }
        } 
        else if (servletPath.startsWith("/admin/")) {
            if (!permissions.contains("admin:manage:users")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ Admin mới có thể truy cập.");
                return;
            }
        }

        // === 4. CHO PHÉP TRUY CẬP ===
        chain.doFilter(req, res);
    }

    @Override public void init(FilterConfig filterConfig) throws ServletException {}
    @Override public void destroy() {}
}

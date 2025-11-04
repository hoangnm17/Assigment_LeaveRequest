package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.user.User;

/**
 * Lớp này override processGet/processPost của BaseController để thêm bước kiểm
 * tra đăng nhập.
 */
public abstract class BaseRequiredAuthenticationController extends BaseController {

    /**
     * Override hook của BaseController
     */
    @Override
    protected final void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("auth");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        processGet(request, response, user);
    }

    /**
     * Override hook của BaseController
     */
    @Override
    protected final void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("auth");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        processPost(request, response, user);
    }

    // Hook mới cho lớp con (ví dụ: trang Profile)
    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException;
}

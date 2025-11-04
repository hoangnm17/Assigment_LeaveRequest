package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base controller cho các trang public.
 * Dùng 'final' để buộc lớp con tuân thủ luồng.
 */
public abstract class BaseController extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGet(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPost(request, response);
    }

    // Hook cho lớp con (public)
    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    // Hook cho lớp con (public)
    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
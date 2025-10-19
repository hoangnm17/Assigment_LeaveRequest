package controller.request;

import dal.LeaveRequestDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import models.LeaveRequest;

/**
 *
 * @author 84911
 */
@WebServlet(name = "ListAppController", urlPatterns = {"/request/list"})
public class ListAppController extends HttpServlet {

    protected void procesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LeaveRequestDBContext list_app_db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> list_app = list_app_db.list();
        request.setAttribute("listApp", list_app);
        request.getRequestDispatcher("../view/leaveRequest/listRequest.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesRequest(request, response);
    }

}

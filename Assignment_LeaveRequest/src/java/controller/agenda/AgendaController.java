package controller.agenda;

import controller.auth.BaseRequiredAuthorizedController;
import dal.EmployeeDAO;
import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.agenda.DailyStatus;
import model.employee.Employee;
import model.agenda.EmployeeWeekAgenda;
import model.leave.LeaveRequest;
import model.user.User;
import utils.ConfigLoader;

@WebServlet(name = "AgendaController", urlPatterns = {"/agenda"})
public class AgendaController extends BaseRequiredAuthorizedController {

    /**
     * SỬA 1: BẮT BUỘC CÓ - Khai báo quyền
     * (Giả sử quyền để xem agenda là "agenda:view")
     */
    @Override
    protected String getRequiredPermission() {
        return ConfigLoader.get("agenda");
    }

    /**
     * SỬA 2: Đổi tên 'doGet' thành 'processGetAuthorized'
     * Toàn bộ logic GỐC của bạn được chuyển vào đây.
     */
    @Override
    protected void processGetAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // --- BẮT ĐẦU LOGIC GỐC CỦA BẠN ---
        EmployeeDAO employeeDAO = new EmployeeDAO();
        LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();

        // 1. Lấy tuần từ request
        String weekStartStr = request.getParameter("weekStart"); // ví dụ "2025-11-03"
        LocalDate weekStart;
        if (weekStartStr == null || weekStartStr.isEmpty()) {
            weekStart = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // mặc định tuần hiện tại
        } else {
            weekStart = LocalDate.parse(weekStartStr);
        }
        LocalDate weekEnd = weekStart.plusDays(6);

        // 2. Lấy danh sách nhân viên (dựa theo quyền)
        ArrayList<Employee> employees;
        
        // SỬA 3: Đổi 'account' thành 'user' cho khớp tên tham số
        employees = employeeDAO.listByManagerHierarchy(user.getEmployee().getId());

        // 3. Lấy danh sách đơn nghỉ phép trong tuần
        List<LeaveRequest> leaveRequests = leaveRequestDAO.listByDateRange(weekStart, weekEnd);

        // 4. Tạo agenda
        List<EmployeeWeekAgenda> agendaList = generateWeekAgenda(employees, leaveRequests, weekStart, weekEnd);

        request.setAttribute("agendaList", agendaList);
        request.setAttribute("weekStart", weekStart);
        request.setAttribute("weekEnd", weekEnd);

        request.getRequestDispatcher("/view/agenda/agenda.jsp").forward(request, response);
        // --- KẾT THÚC LOGIC GỐC CỦA BẠN ---
    }

    /**
     * SỬA 4: Đổi tên 'doPost' thành 'processPostAuthorized'
     * Trang Agenda thường chỉ GET, nếu POST (ví dụ lọc) thì gọi lại GET là hợp lý
     */
    @Override
    protected void processPostAuthorized(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        
        // Gọi lại logic GET để tải lại trang (ví dụ: khi chọn tuần)
        processGetAuthorized(request, response, user);
    }
    
    /**
     * Phương thức private helper (giữ nguyên, đã đúng)
     */
    private List<EmployeeWeekAgenda> generateWeekAgenda(
            List<Employee> employees,
            List<LeaveRequest> leaveRequests,
            LocalDate startOfWeek,
            LocalDate endOfWeek) {

        List<EmployeeWeekAgenda> result = new ArrayList<>();

        // 🌟 Gom nhóm danh sách nghỉ phép theo nhân viên
        Map<Integer, List<LeaveRequest>> leaveMap = new HashMap<>();
        for (LeaveRequest lr : leaveRequests) {
            int empId = lr.getCreated_by().getId();
            leaveMap.computeIfAbsent(empId, k -> new ArrayList<>()).add(lr);
        }

        // 🌟 Duyệt từng nhân viên
        for (Employee emp : employees) {
            ArrayList<DailyStatus> weekStatus = new ArrayList<>();

            for (LocalDate d = startOfWeek; !d.isAfter(endOfWeek); d = d.plusDays(1)) {
                String status;

                // Thứ 7 & CN => weekend
                if (d.getDayOfWeek() == java.time.DayOfWeek.SATURDAY
                        || d.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
                    status = "weekend";
                } // Trong thời gian làm việc => work
                else if ((emp.getHireDate() == null || !d.isBefore(emp.getHireDate().toLocalDate()))
                        && (emp.getTerminationDate() == null || !d.isAfter(emp.getTerminationDate().toLocalDate()))) {
                    status = "work";
                } // Ngoài thời gian làm việc
                else {
                    status = "none";
                }

                // Gán ngày nghỉ phép
                List<LeaveRequest> empLeaves = leaveMap.get(emp.getId());
                if (empLeaves != null) {
                    for (LeaveRequest lr : empLeaves) {
                        LocalDate start = lr.getStartDate().toLocalDate();
                        LocalDate end = lr.getEndDate().toLocalDate();
                        if (!d.isBefore(start) && !d.isAfter(end)) {
                            status = "leave";
                            break;
                        }
                    }
                }
                weekStatus.add(new DailyStatus(d, status));
            }
            result.add(new EmployeeWeekAgenda(emp, weekStatus));
        }
        return result;
    }

}
package model.agenda;

import model.employee.Employee;
import java.util.ArrayList;

// Lưu thông tin nhân viên và trạng thái cả tuần
public class EmployeeWeekAgenda {
    private Employee employee;
    private ArrayList<DailyStatus> weekStatus;

    public EmployeeWeekAgenda(Employee employee, ArrayList<DailyStatus> weekStatus) {
        this.employee = employee;
        this.weekStatus = weekStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ArrayList<DailyStatus> getWeekStatus() {
        return weekStatus;
    }

    public void setWeekStatus(ArrayList<DailyStatus> weekStatus) {
        this.weekStatus = weekStatus;
    }
}

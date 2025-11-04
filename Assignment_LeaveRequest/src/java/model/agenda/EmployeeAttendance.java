package model.agenda;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import models.BaseModel;

public class EmployeeAttendance extends BaseModel{

    private String employeeName;
    private Map<Date, String> statusMap; // ngày -> WORK / LEAVE

    public EmployeeAttendance() {
        statusMap = new HashMap<>();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Map<Date, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Date, String> statusMap) {
        this.statusMap = statusMap;
    }

    public void addStatus(Date date, String status) {
        statusMap.put(date, status); // status: "WORK" hoặc "LEAVE"
    }
}

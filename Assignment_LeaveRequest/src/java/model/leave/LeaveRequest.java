package model.leave;

// Dùng java.util.Date để lưu cả ngày và giờ
import java.util.Date; 
import models.BaseModel;
import model.employee.Employee;

public class LeaveRequest extends BaseModel {

    // 1. Sửa: Dùng Employee và đổi tên cho khớp với CSDL (Created_by)
    private Employee created_by; 
    private LeaveType leaveType;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private String reason;
    private String status;
    private Date created_time;
    private ApprovalStep appStep;

    public ApprovalStep getAppStep() {
        return appStep;
    }

    public void setAppStep(ApprovalStep appStep) {
        this.appStep = appStep;
    }

    // --- Getter/Setter đã cập nhật ---

    public Employee getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Employee created_by) {
        this.created_by = created_by;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

}
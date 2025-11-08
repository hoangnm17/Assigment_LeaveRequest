package utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestValidator {

    private int leaveTypeId;
    private Date startDate;
    private Date endDate;
    private String reason;

    public LeaveRequestValidator(String leaveTypeParam, String startDateParam, String endDateParam, String reasonParam) {
        this.reason = reasonParam;

        // Chuyển leaveType
        try {
            this.leaveTypeId = Integer.parseInt(leaveTypeParam);
        } catch (NumberFormatException ex) {
            this.leaveTypeId = -1; // giá trị không hợp lệ
        }

        // Chuyển ngày
        try {
            this.startDate = Date.valueOf(startDateParam);
        } catch (IllegalArgumentException ex) {
            this.startDate = null;
        }

        try {
            this.endDate = Date.valueOf(endDateParam);
        } catch (IllegalArgumentException ex) {
            this.endDate = null;
        }
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (leaveTypeId <= 0) {
            errors.add("Loại nghỉ không hợp lệ.");
        }

        if (startDate == null) {
            errors.add("Ngày bắt đầu không hợp lệ.");
        }

        if (endDate == null) {
            errors.add("Ngày kết thúc không hợp lệ.");
        }

        if (startDate != null && endDate != null && endDate.before(startDate)) {
            errors.add("Ngày kết thúc phải sau ngày bắt đầu.");
        }

        if (reason == null || reason.trim().isEmpty()) {
            errors.add("Lý do nghỉ không được để trống.");
        }

        return errors;
    }

    // Getter
    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }
}

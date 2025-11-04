package model.leave;

import model.leave.LeaveRequest;
import java.sql.Date;
import models.BaseModel;
import model.user.User;

public class ApprovalStep extends BaseModel {

    private LeaveRequest request;
    private User manager; // ID của người duyệt
    private String status;
    private java.sql.Date actionDate; // Có thể null
    private String notes; // Có thể null
    private boolean isViewed;

    public LeaveRequest getRequest() {
        return request;
    }

    public void setRequest(LeaveRequest request) {
        this.request = request;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isIsViewed() {
        return isViewed;
    }

    public void setIsViewed(boolean isViewed) {
        this.isViewed = isViewed;
    }
    
    
}

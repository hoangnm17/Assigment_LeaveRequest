package model.leave;

import model.leave.LeaveRequest;
import java.sql.Date;
import models.BaseModel;

public class RequestAttachment extends BaseModel {

    private LeaveRequest request;
    private String fileName;
    private String filePath;
    private java.sql.Date uploadedAt;

    public LeaveRequest getRequest() {
        return request;
    }

    public void setRequest(LeaveRequest request) {
        this.request = request;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}

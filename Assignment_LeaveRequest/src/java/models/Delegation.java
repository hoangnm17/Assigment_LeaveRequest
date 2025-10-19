

package models;

import java.sql.Date;

public class Delegation extends BaseModel {
    private User originalApprover;
    private User delegateApprover;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    public User getOriginalApprover() {
        return originalApprover;
    }

    public void setOriginalApprover(User originalApprover) {
        this.originalApprover = originalApprover;
    }

    public User getDelegateApprover() {
        return delegateApprover;
    }

    public void setDelegateApprover(User delegateApprover) {
        this.delegateApprover = delegateApprover;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

package models;

public class WorkFlowRule extends BaseModel {

    private LeaveType leaveType;
    private ApprovalStep stepOrder;
    private String approverRole;

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public ApprovalStep getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(ApprovalStep stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getApproverRole() {
        return approverRole;
    }

    public void setApproverRole(String approverRole) {
        this.approverRole = approverRole;
    }
    
    
}

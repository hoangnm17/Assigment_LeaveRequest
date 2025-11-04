package model.user;

import java.io.Serializable;

public class RoleFeature implements Serializable {

    private int roleID;
    private int featureID;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getFeatureID() {
        return featureID;
    }

    public void setFeatureID(int featureID) {
        this.featureID = featureID;
    }
}

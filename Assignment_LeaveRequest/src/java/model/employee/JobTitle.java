package model.employee;

import models.BaseModel;

public class JobTitle  extends BaseModel {
    private String titleName;
    private String description;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

package net.seehope.pojo;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "`send`")
public class Send implements Serializable {

    @Column(name = "`user_type`")
    private String userType;


    @Column(name = "`time`")
    private String time;

    @Column(name = "`manager_name`")
    private String managerName;


    @Column(name = "`information`")
    private String information;


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}

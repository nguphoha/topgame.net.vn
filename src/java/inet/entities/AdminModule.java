/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;


import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
@Table(name = "admin_module")
public class AdminModule implements Serializable {

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "module_id")
    private String moduleId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

}

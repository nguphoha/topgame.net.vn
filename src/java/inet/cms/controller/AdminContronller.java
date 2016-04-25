/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cms.controller;

import inet.dao.AdminDao;
import inet.entities.Admin;
import inet.util.Constants;
import inet.util.StringUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import inet.controller.BaseController;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class AdminContronller extends BaseController {

    String username;
    String status;

    String password;
    String confirmPassword;

    List<Admin> datas;
    Admin selectedAdmin;

    AdminDao adminDao;
    Admin admin = new Admin();

    public AdminContronller() {
        try {
            adminDao = new AdminDao();
            datas = adminDao.getDatas(username, status);
        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public void search() {
        try {
            datas = adminDao.getDatas(username, status);
            selectedAdmin = null;
        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public void insert() {
        try {
            if (admin.getUsername().trim().equals("")) {
                addErrorMessage("Vui lòng nhập username");
                return;
            }

            if (admin.getPassword().trim().equals("")) {
                addErrorMessage("Vui lòng nhập password");
                return;
            }
            admin.insert();
            datas.add(admin);
            admin = new Admin();

            addSuccessMessage("Thêm tài khoản admin thành công");
        } catch (Exception ex) {
            logToError(ex);
            addSuccessMessage("Đã xảy ra lỗi trong quá trình xử lý");
        }
    }

    public void delete() {
        try {
            adminDao.delete(selectedAdmin);
            datas.remove(selectedAdmin);
            selectedAdmin = null;
        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public void update() {
        try {
            if (selectedAdmin.getUsername().trim().equals("")) {
                addErrorMessage("Vui lòng nhập username");
                return;
            }

            if (selectedAdmin.getPassword().trim().equals("")) {
                addErrorMessage("Vui lòng nhập password");
                return;
            }

            selectedAdmin.setUsername(selectedAdmin.getUsername().trim());
            selectedAdmin.setPassword(selectedAdmin.getPassword().trim());
            adminDao.update(selectedAdmin);
            addSuccessMessage("Sửa tài khoản admin thành công");

        } catch (Exception ex) {
            logToError(ex);
            addSuccessMessage("Đã xảy ra lỗi trong quá trình xử lý");
        }
    }

    public void changePassword() {
        try {
            if (StringUtil.nvl(password.trim(), "").equals("")) {
                addErrorMessage("Vui lòng nhập mật khẩu");
                return;
            }

            if (StringUtil.nvl(confirmPassword.trim(), "").equals("")) {
                addErrorMessage("Vui lòng xác nhận mật khẩu");
                return;
            }

            if (!password.trim().equals(confirmPassword.trim())) {
                addErrorMessage("Xác nhận mật khẩu chưa đúng");
                return;
            }

            Admin admin = (Admin) getSessionValue(Constants.ADMIN);
            admin.setPassword(password);
            admin.update();

            addSuccessMessage("Cập nhật mật khẩu thành công");
        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Admin> getDatas() {
        return datas;
    }

    public void setDatas(List<Admin> datas) {
        this.datas = datas;
    }

    public Admin getSelectedAdmin() {
        return selectedAdmin;
    }

    public void setSelectedAdmin(Admin selectedAdmin) throws CloneNotSupportedException {
        this.selectedAdmin = selectedAdmin.clone();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}

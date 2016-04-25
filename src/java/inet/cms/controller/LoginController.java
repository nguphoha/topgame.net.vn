/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cms.controller;

import inet.controller.BaseController;
import inet.dao.AdminDao;
import inet.entities.Admin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import inet.util.Constants;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class LoginController extends BaseController{

    private String username;
    private String password;
    /**
     * Creates a new instance of CMSLoginController
     */
    public LoginController() {
        super();
    }

    public void login() {
        try {
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.login(username, password);
            if (admin == null) {
                addErrorMessage("Sai tên đăng nhập hoặc mật khẩu!");
            } else {
                getSessions().put(Constants.ADMIN, admin);
                redirect(getContextPath() + "/cms/index.xhtml");
            }
        } catch (Exception ex) {
            logToError(ex);
        }
    }
    
    public void logout() {
        try {
            getSessions().clear();
            redirect(getContextPath() + "/cms/login.xhtml");
        } catch (IOException ex) {
            logToError(ex);
        }
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.cache.AccountCache;
import inet.cache.management.CacheFactory;
import inet.dao.AccountDao;
import inet.entities.Account;
import inet.util.Constants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class LoginController extends BaseController{

    private String mobile, password, message;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    public void login(){
        try {
            
            AccountDao accDao = new AccountDao();
            Account account = accDao.login(mobile, password);
            if(account != null){
                setSessionValue(Constants.ACCOUNT, account);
                setSessionValue(Constants.MOBILE, account.getMobile());
                redirect(getContextPath() + "/trang-ca-nhan.html");
            }else{
                message = "Sai mật khẩu hoặc số điện thoại chưa đăng ký";
            }
        } catch (Exception ex) {
            logToError("login with mobile "+ mobile + " password "+ password +" error " + ex.getMessage());
        }
    }
    
    public void register(){
        System.out.println("==========================");
        System.out.println("register in action");
        System.out.println("==========================");
    }
    
    public String getUsername() {
        return mobile;
    }

    public void setUsername(String username) {
        this.mobile = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}

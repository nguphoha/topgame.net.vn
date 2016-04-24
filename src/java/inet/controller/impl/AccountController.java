/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.controller.BaseController;
import inet.dao.AccountDao;
import inet.entities.Account;
import inet.entities.AccountService;
import inet.util.Constants;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author TOM
 */
@ManagedBean
@RequestScoped
public class AccountController extends BaseController {

    String mobile = "";
    String password = "";

    public void login() {
        try {

            if (mobile.startsWith("0")) {
                mobile = mobile.replaceFirst("0", "84");
            }

            AccountDao dao = new AccountDao();
            Account account = dao.login(mobile, password);

            if (account == null) {
                alert("Sai số điện thoại hoặc mật khẩu!");
                return;
            }

            AccountService service = account.getService(Constants.SERVICE_CODE);

            if (service.getStatus().equals(Account.ACTIVE)) {
                setSessionValue(Constants.ACCOUNT, account);
                redirect(getRealUri() + "/trang-ca-nhan.html");
            } else if (service.getStatus().equals(Account.PENDING)) {
                alert("Tài khoản của bạn không gia hạn thành công do không đủ tiền trong tài khoản. Vui lòng nạp tiền vào tài khoản.");
            } else {
                alert("Đăng nhập không thành công do bạn đã hủy dịch vụ trước đó");
            }

        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public void alert(String message) {
        RequestContext.getCurrentInstance().execute("alert('" + message + "')");
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

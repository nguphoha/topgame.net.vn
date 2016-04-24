/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps;

import com.viettel.iwebgate.rsa.encrypt.EncryptManager;
import inet.cache.ServiceCache;
import inet.cache.management.CacheFactory;
import inet.common.log.Logger;
import inet.dao.AccountDao;
import inet.entities.Account;
import inet.entities.AccountLog;
import inet.entities.AccountService;
import inet.entities.Service;
import inet.util.Constants;
import inet.util.StringUtil;
import inet.util.TimestampUtil;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author TOM
 */
@WebServlet(name = "ChargeServlet", urlPatterns = {"/charge.html"})
public class MPSResponseServlet extends HttpServlet {

    Logger logger = new Logger("charge");
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {

            Map<String, String> params = MPSService.parseQueryString(request, response);
            String requestId = params.get("REQ");

            if (requestId == null) {
                response.sendRedirect("http://topgame.net.vn");
            } else if (session.getAttribute(requestId) != null) {
                String command = (String) session.getAttribute(requestId);
                if (command.equals(MPSService.CMD_REGISTER)) {
                    subscribe(params, request, response);
                } else if (command.equals(MPSService.CMD_DETECT_MOBILE)) {
                    detectMobile(params, request, response);
                } else {
                    response.sendRedirect("http://topgame.net.vn");
                }
            } else {
                response.sendRedirect("http://topgame.net.vn");
            }
        } catch (Exception ex) {
            logger.info(ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void subscribe(Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String requestId = StringUtil.nvl(params.get("REQ"), "");
        String responseCode = StringUtil.nvl(params.get("RES"), "");
        String mobile = StringUtil.nvl(params.get("MOBILE"), "");
        String command = StringUtil.nvl(params.get("CMD"), "");
        String price = StringUtil.nvl(params.get("PRICE"), "");
        String channel = StringUtil.nvl(params.get("SOURCE"), "");

        Timestamp createDate = new Timestamp(System.currentTimeMillis());

        logger.info("REQ = " + requestId + " | RES = " + responseCode + " | MOBILE = " + mobile + " | CMD = " + command + " | PRICE = " + price + " | CHANNEL = " + channel);

        if (responseCode.equals(MPSService.SUCCESS)) {

            mobile = "84" + mobile;

            Service service = ((ServiceCache) CacheFactory.getCache("service")).getByCode(Constants.SERVICE_CODE);
            AccountService accountService;

            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getByMobile(mobile);

            if (account == null) { // THUÊ BAO ĐĂNG KÝ LẦN ĐẦU TIÊN

                account = new Account();
                account.setMobile(mobile);
                account.setKey(EncryptManager.AESKeyGen());
                account.setDateCreate(createDate);
                account.setStatus(Account.ACTIVE);
                account.setPassword(StringUtil.generate(6, StringUtil.ALPHA).toUpperCase());
                account.insert();

                accountService = new AccountService();
                accountService.setAccountId(account.getId());
                accountService.setServiceId(service.getId());
                accountService.setServiceCode(service.getCode());
                accountService.setMobile(account.getMobile());
                accountService.setDateCreate(createDate);
                accountService.setDateLatestCreate(createDate);
                accountService.setDateExpire(TimestampUtil.addDay(createDate, service.getChargeCycle()));
                accountService.setPartner("LIFESOFT");
                accountService.setUrlRegister("");
                accountService.setStatus(Account.ACTIVE);
                accountService.setProperties("");
                accountService.setChannel(Account.CHANNEL_SMS);
                accountService.insert();

                account.putService(service.getCode(), accountService);

            } else { // THUÊ BAO ĐĂNG KÝ LẠI DỊCH VỤ

                accountService = account.getService(service.getCode());
                if (accountService == null) {

                    accountService = new AccountService();
                    accountService.setAccountId(account.getId());
                    accountService.setServiceId(service.getId());
                    accountService.setServiceCode(service.getCode());
                    accountService.setMobile(account.getMobile());
                    accountService.setDateCreate(createDate);
                    accountService.setDateLatestCreate(createDate);
                    accountService.setDateExpire(TimestampUtil.addDay(createDate, service.getChargeCycle()));
                    accountService.setPartner("LIFESOFT");
                    accountService.setUrlRegister("");
                    accountService.setStatus(Account.ACTIVE);
                    accountService.setProperties("");
                    accountService.setChannel(Account.CHANNEL_SMS);
                    accountService.insert();
                    account.putService(service.getCode(), accountService);

                } else {

                    if (createDate.getTime() < accountService.getDateExpire().getTime()) { // ĐĂNG KÝ LẠI TRONG CÙNG CHU KỲ CƯỚC => GIÁ = 0 VND
                        price = "0";
                    }

                    accountService.setDateLatestCreate(createDate);
                    accountService.setDateExpire(TimestampUtil.addDay(createDate, service.getChargeCycle()));
                    accountService.setStatus(Account.ACTIVE);
                    accountService.setChannel(Account.CHANNEL_SMS);
                    accountService.setUrlRegister("");
                    accountService.setProperties("");
                    accountService.update();

                }
            }

            AccountLog accountLog = new AccountLog();
            accountLog.setMobile(mobile);
            accountLog.setAmount(price);
            accountLog.setAccountId(account.getId());
            accountLog.setPartner(accountService.getPartner());
            accountLog.setServiceCode(service.getCode());
            accountLog.setServiceId(service.getId());
            accountLog.setResult(AccountLog.SUCCESS);
            accountLog.setType(AccountLog.TYPE_REGISTER);
            accountLog.setDateCreate(createDate);
            accountLog.insert();

            sendMT(Constants.MESSAGE_REGISTER_SUCCESS, mobile, Constants.SERVICE_NUMBER);
            sendMT(Constants.MESSAGE_PASSWORD, mobile, Constants.SERVICE_NUMBER);

        }

        response.sendRedirect("http://topgame.net.vn");

    }

    public void detectMobile(Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String requestId = StringUtil.nvl(params.get("REQ"), "");
        String responseCode = StringUtil.nvl(params.get("RES"), "");
        String mobile = StringUtil.nvl(params.get("MOBILE"), "");
        String command = StringUtil.nvl(params.get("CMD"), "");
        String price = StringUtil.nvl(params.get("PRICE"), "");
        String channel = StringUtil.nvl(params.get("SOURCE"), "");

        logger.info("REQ = " + requestId + " | RES = " + responseCode + " | MOBILE = " + mobile + " | CMD = " + command + " | PRICE = " + price + " | CHANNEL = " + channel);

        if (mobile.equals("")) {

            response.sendRedirect("http://topgame.net.vn");
            return;

        }

        if (!mobile.startsWith("84")) {

            mobile = "84" + mobile;

        }

        request.getSession().setAttribute(Constants.MOBILE, mobile);

        AccountDao dao = new AccountDao();
        Account account = dao.getByMobile(mobile);
        if (account != null) {
            request.getSession().setAttribute(Constants.ACCOUNT, account);
        }

        response.sendRedirect("http://topgame.net.vn");
    }

    public void sendMT(String message, String mobile, String serviceNumber) throws Exception {

    }

}

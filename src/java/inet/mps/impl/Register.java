/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps.impl;

import com.viettel.iwebgate.rsa.encrypt.EncryptManager;
import inet.mps.MPSService;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class Register extends MPSService {

    public static final String REDIRECT_URL = "http://vas.vietteltelecom.vn/MPS/charge.html";

    @Override
    protected void buildParams(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        String mobile = (String) session.getAttribute("mobile");
        String sub = request.getParameter("serviceCode");
        String price = request.getParameter("price");

        String requestId = getRequestID();
        String value = "SUB=" + sub + "&CATE=" + CATE + "&ITEM=DK LS" + "&SUB_CP=" + SUB_CP + "&CONT=" + CONT + "&PRICE=" + price + "&REQ=" + requestId + "&MOBILE=" + mobile + "&SOURCE=WAP";

        String keyAES = EncryptManager.AESKeyGen();
        String encryptedValue = EncryptManager.encryptAES(value, keyAES);
        encryptedValue = "value=" + encryptedValue + "&key=" + keyAES;

        String data = EncryptManager.encryptRSA(encryptedValue, VIETTEL_PUBLIC_KEY);
        String signature = URLEncoder.encode(EncryptManager.createMsgSignature(data, LIFESOFT_PRIVATE_KEY), "UTF-8");

        String params = "PRO=" + PRO + "&CMD=" + CMD_REGISTER + "&SER=" + SER + "&SUB=" + SUB;
        params = params + "&DATA=" + URLEncoder.encode(data, "UTF-8") + "&SIG=" + signature;

        session.setAttribute(requestId, MPSService.CMD_REGISTER);

        response.sendRedirect(REDIRECT_URL + "?" + params);

    }

}

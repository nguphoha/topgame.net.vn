/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps.impl;

import com.viettel.iwebgate.rsa.encrypt.EncryptManager;
import inet.mps.MPSService;
import inet.util.Constants;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class DetectMobile extends MPSService {

    public static final String REDIRECT_URL = "http://vas.vietteltelecom.vn/MPS/mobile.html";

    @Override
    protected void buildParams(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String sessionId = session.getId();

        String requestId = getRequestID();
        String encryptedValue = null;
        String data = null;
        String signature = null;

        String value = "SUB=" + SUB + "&REQ=" + requestId + "&SESS=" + sessionId + "&SOURCE=" + SOURCE;

        String aESKey = EncryptManager.AESKeyGen();
        encryptedValue = EncryptManager.encryptAES(value, aESKey);
        encryptedValue = "value=" + encryptedValue + "&key=" + aESKey;

        data = EncryptManager.encryptRSA(encryptedValue, VIETTEL_PUBLIC_KEY);
        signature = URLEncoder.encode(EncryptManager.createMsgSignature(data, LIFESOFT_PRIVATE_KEY), "UTF-8");

        String params = "PRO=" + PRO + "&SER=" + SER + "&SUB=" + SUB;
        params = params + "&DATA=" + URLEncoder.encode(data, "UTF-8") + "&SIG=" + signature;

        session.setAttribute(Constants.COMMAND, MPSService.CMD_DETECT_MOBILE);

        response.sendRedirect(REDIRECT_URL + "?" + params);

    }

}

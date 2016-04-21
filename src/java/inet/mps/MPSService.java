/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps;

import com.viettel.iwebgate.rsa.encrypt.EncryptManager;
import inet.util.StringUtil;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TOM
 */
public abstract class MPSService {

//    public static final String TRANSACTION = "TRANSACTION";
    public static final String CMD_REGISTER = "REGISTER";
    public static final String CMD_CANCEL = "CANCEL";
    public static final String CMD_DEPOSIT = "DEPOSIT";
    public static final String CMD_MONFEE = "MONFEE";
    public static final String CMD_GET_CONTENT = "CONTENT";
    public static final String CMD_MO = "MO";
    public static final String CMD_DETECT_MOBILE = "MOBILE";

    public static final String PRO = "";
    public static final String SER = "";
    public static final String CP_CODE = "";
    public static final String SOURCE = "WAP";
    public static final String CATE = "Game";
    public static final String SUB_CP = "LifeSoft";
    public static final String SUB = "Đăng ký dịch vụ TopGame - 2000/ngày";
    public static final String CONT = "TopGame Subscribe";

    public void redirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        buildParams(request, response);
    }

    protected abstract void buildParams(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public static synchronized String getRequestID() {
        try {
            Thread.sleep(1);
            return String.valueOf(System.currentTimeMillis());
        } catch (InterruptedException ex) {
            return "";
        }
    }

    public static Map<String, String> parseQueryString(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String queryString = request.getQueryString();
        Map params = parseQueryString(queryString);

        String data = (String) params.get("DATA");
        String signature = (String) params.get("SIG");
        String value = null;
        String aesKey = null;

        Map<String, String> resParams = new HashMap<String, String>();

        if (data != null && signature != null) {

            signature = URLDecoder.decode(signature, "UTF-8");
            Boolean verify = EncryptManager.verifyMsgSignature(signature, VIETTEL_PUBLIC_KEY, data);

            if (verify) {

                data = EncryptManager.decryptRSA(data, LIFESOFT_PRIVATE_KEY);
                resParams = parseQueryString(data);
                value = (String) resParams.get("VALUE");
                aesKey = (String) resParams.get("KEY");

                value = EncryptManager.decryptAES(value, aesKey);
                resParams = parseQueryString(value);
            }
        }

        return resParams;

    }

    private static Map<String, String> parseQueryString(String queryString) {

        Map<String, String> params = new HashMap<String, String>();
        String[] cParams = StringUtil.toStringArray(queryString, "&");

        String name = null;
        String value = null;

        for (String item : cParams) {

            if (item.indexOf("=") <= 0) {
                continue;
            }

            name = item.substring(0, item.indexOf("="));
            value = item.substring(item.indexOf("=") + 1);
            params.put(name, value);
        }

        return params;
    }

    public static final String VIETTEL_PUBLIC_KEY = "";
    public static final String LIFESOFT_PUBLIC_KEY = "";
    public static final String LIFESOFT_PRIVATE_KEY = "";

}

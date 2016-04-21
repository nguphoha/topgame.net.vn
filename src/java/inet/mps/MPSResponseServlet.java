/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps;

import java.io.IOException;
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
                    subscribe(params);
                } else if (command.equals(MPSService.CMD_DETECT_MOBILE)) {
                    detectMobile(params);
                } else {
                    response.sendRedirect("http://topgame.net.vn");
                }
            } else {
                response.sendRedirect("http://topgame.net.vn");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void subscribe(Map<String, String> params) {

        // Viết nghiệp vụ đăng ký
    }

    public void detectMobile(Map<String, String> params) {

        // Viết nghiệp vụ nhận diện thuê bao
    }

}

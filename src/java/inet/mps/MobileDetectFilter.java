/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.mps;

import inet.common.log.Logger;
import inet.mps.MPSService;
import inet.mps.impl.DetectMobile;
import inet.util.Constants;
import inet.util.StringUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TOM
 */
public class MobileDetectFilter implements Filter {

    Logger logger = new Logger("console");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = (HttpSession) request.getSession();
        String isMobileDetect = StringUtil.nvl(session.getAttribute(Constants.IS_MOBILE_REDIRECT), "");
        try {
            if (!isMobileDetect.equals("")) {

                session.setAttribute(Constants.IS_MOBILE_REDIRECT, "TRUE");

                MPSService service = new DetectMobile();
                service.redirect(request, response);
                
            } else {
                chain.doFilter(req, resp);
            }
        } catch (Exception ex) {
            logger.info(ex);
        }
    }

    @Override
    public void destroy() {

    }

}

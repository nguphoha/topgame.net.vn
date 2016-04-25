/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.filter;

import inet.entities.Admin;
import inet.entities.Module;
import inet.util.Constants;
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
 * @author Admin
 */
public class PermissionFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        
        Admin admin = (Admin) session.getAttribute(Constants.ADMIN);
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.xhtml");
        } else {
            String url = request.getRequestURI();
            if (url.equals("/")) {
                url = "/index.xhtml";
            }
            Module module = admin.getModule(url);
            if (module == null && url.equals("/index.xhtml")) {
                chain.doFilter(req, resp);
            } else if (module == null && !url.equals("/index.xhtml")) {
                response.sendRedirect(request.getContextPath() + "/index.xhtml");
            } else {
                chain.doFilter(req, resp);
            }
        }
        
    }
    
    @Override
    public void destroy() {
        
    }
    
}

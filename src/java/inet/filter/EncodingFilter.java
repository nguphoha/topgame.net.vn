/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.filter;

import java.io.IOException;
import javax.servlet.*;

/**
 *
 * @author Administrator
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        // pass the request on
        chain.doFilter(request, response);

        //Setting the character set for the response
        response.setCharacterEncoding("UTF-8");

    }

    @Override
    public void destroy() {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.common.jsf.handler;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class CustomViewHandler extends ViewHandlerWrapper {

    private ViewHandler wrappped;

    public CustomViewHandler(ViewHandler wrappped) {
        super();
        this.wrappped = wrappped;
    }

    @Override
    public ViewHandler getWrapped() {
        return wrappped;
    }

    @Override
    public String getActionURL(FacesContext context, String viewId) {
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String url = super.getActionURL(context, viewId);
        if (req.getAttribute("javax.servlet.forward.request_uri") != null) {
            url = req.getAttribute("javax.servlet.forward.request_uri").toString();
        }
        return url;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.common.log.Logger;
import inet.constant.Constant;
import inet.entities.Pagination;
import inet.entities.UserAgent;
import inet.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tutl
 */
public class BaseController implements Serializable {

    protected int curentPage = 1;
    protected List<Pagination> paginations;
    protected int pageSize = Constant.PAGE_SIZE;
    private static Logger console = new Logger("console");
    private static Logger error = new Logger("error");
    public boolean isMobile = false;

    static {
        error.setDebug(true);
        console.setDebug(true);
    }

    public BaseController() {
        isMobile = getUserAgentInfo().isMobilePhone;
    }

    private UserAgent getUserAgentInfo() {
        return new UserAgent(getRequest().getHeader("user-agent"), getRequest().getHeader("Accept"));
    }

    public String routing() {
        return isMobile ? "wap" : "web";
    }

    public void pagination(int dataSize) {

        int display = 5;

        // Calculate number of page
        int phanNguyen = dataSize / pageSize;
        int phanDu = dataSize % pageSize;
        int soTrang = phanNguyen;
        if (phanDu > 0) {
            soTrang += 1;
        }

        if (soTrang == 0) {
            soTrang = 1;
        }

        int i = 1;
        if (curentPage == 1 || curentPage == 2 || curentPage == 3) {
            i = 1;
        } else {
            i = curentPage - 2;
            display = curentPage + 2;
        }

        if (display > soTrang) {
            display = soTrang;
        }

        // init paginations array
        paginations = new ArrayList<Pagination>();

        // add first page
        Pagination pagination = new Pagination();
        pagination.setLabel("<<");
        pagination.setValue(1);
        pagination.setRender(true);

        if (curentPage > 1) {
            pagination.setDisable(false);
        } else {
            pagination.setDisable(true);
        }
        pagination.setStyle("SoTrang_number1");
        paginations.add(pagination);

        // add previous page
        pagination = new Pagination();
        pagination.setLabel("<");
        pagination.setValue(curentPage - 1);
        pagination.setRender(true);

        if (curentPage > 1) {
            pagination.setDisable(false);
        } else {
            pagination.setDisable(true);
        }
        pagination.setStyle("SoTrang_number1");
        paginations.add(pagination);

        // add page items
        for (; i <= display; i++) {
            pagination = new Pagination();

            pagination.setRender(true);
            pagination.setValue(i);
            pagination.setLabel(String.valueOf(i));
            if (i == curentPage) {
                pagination.setStyle("SoTrang_number");
                pagination.setDisable(true);
            } else {
                pagination.setStyle("SoTrang_number1");
                pagination.setDisable(false);
            }
            paginations.add(pagination);
        }

        // add next page
        pagination = new Pagination();
        pagination.setLabel(">");
        pagination.setValue(curentPage + 1);
        pagination.setRender(true);
//
        if (curentPage == soTrang || soTrang == 0) {
            pagination.setDisable(true);
        } else {
            pagination.setDisable(false);
        }
        pagination.setStyle("SoTrang_number1");
        paginations.add(pagination);

        // add end page
        pagination = new Pagination();
        pagination.setLabel(">>");
        pagination.setValue(soTrang);
        pagination.setRender(true);

        if (curentPage == soTrang || soTrang == 0) {
            pagination.setDisable(true);
        } else {
            pagination.setDisable(false);
        }
        pagination.setStyle("SoTrang_number1");
        paginations.add(pagination);
    }

    public void setAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    public Object getAttribute(String key) {
        return getRequest().getAttribute(key);
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public void addFatalMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
    }

    public void addWarningMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
    }

    public String getUserAgent() {
        return getRequest().getHeader("user-agent");
    }

    public String getParameter(String key) {
        return StringUtil.nvl(getExternalContext().getRequestParameterMap().get(key), "");
    }

    public String getParameter(String key, String nvl) {
        return StringUtil.nvl(getExternalContext().getRequestParameterMap().get(key), nvl);
    }

    public void setParameter(String key, Object value) {
        getParameters().put(key, value);
    }

    public Map getParameters() {
        return getExternalContext().getRequestParameterMap();
    }

    public Map getSessions() {
        return getExternalContext().getSessionMap();
    }

    public Object getSessionValue(String key) {
        return getSessions().get(key);
    }

    public void setSessionValue(String key, Object value) {
        getSessions().put(key, value);
    }

    public Map getApplications() {
        return getExternalContext().getApplicationMap();
    }

    public Object getApplicationValue(String key) {
        return getApplications().get(key);
    }

    public void setApplicationValue(String key, Object value) {
        getApplications().put(key, value);
    }

    public String getContextPath() {
        return getExternalContext().getRequestContextPath();
    }

    public String getContentType() {
        return getExternalContext().getRequestContentType();
    }

    public String getIpAddress() {
        return getExternalContext().getRemoteUser();
    }

    public String getRealPath(String path) {
        return getExternalContext().getRealPath(path);
    }

    public InputStream getResourceAsStream(String filePath) {
        return getExternalContext().getResourceAsStream(filePath);
    }

    public String getRealPath() {
        return getRequest().getServletContext().getRealPath("/");
    }

    public String getRealUri() {
        HttpServletRequest request = getRequest();
        return "http://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath();
    }

    public String getMimeType(String filePath) {
        return getExternalContext().getMimeType(filePath);
    }

    public int getCurentPage() {
        return curentPage;
    }

    public void setCurentPage(int curentPage) {
        this.curentPage = curentPage;
    }

    public List<Pagination> getPaginations() {
        return paginations;
    }

    public void setPaginations(List<Pagination> paginations) {
        this.paginations = paginations;
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public void redirect(String path) throws IOException {
        getExternalContext().redirect(path);
    }

    protected void logToConsole(String message) {
        console.info(message);
    }

    protected void logToError(String messeage) {
        error.info(messeage);
    }

    protected void logToConsole(Exception ex) {
        console.info(ex);
    }

    protected void logToError(Exception ex) {
        error.info(ex);
    }

    public String urlFriendly(String s) {
        if (s == null) {
            return "";
        }
        s = StringUtil.standardized(s);
        s = StringUtil.clearUnicode(s);
        s = s.replaceAll("[!@#()$%^&*~`+.^:,;\"'?<>/“”]", "").replaceAll("–", "").replace(" ", "-");
        return s.toLowerCase();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isIsMobile() {
        return isMobile;
    }

    public void setIsMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

}

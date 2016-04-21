/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.router;

import com.ocpsoft.pretty.PrettyContext;
import inet.controller.BaseController;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author TUTL
 */
@ManagedBean
public class ExpertRouter extends BaseController {

    private String id;
    private String code;

    public String routing() {

        PrettyContext context = PrettyContext.getCurrentInstance();
        String viewId = context.getCurrentMapping().getId();

        if (isMobile) {
            return "/wap/discussion/index.xhtml";
        } else {
            return "/web/discussion/index.xhtml";
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

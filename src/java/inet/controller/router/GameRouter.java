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
public class GameRouter extends BaseController {

//    private String id;
//    private String code;

    public String routing() {

//        PrettyContext context = PrettyContext.getCurrentInstance();
//        String viewId = context.getCurrentMapping().getId();

        if (isMobile) {
            return "/wap/game/index.xhtml";
        } else {
            return "/web/game/index.xhtml";
        }

    }    

}

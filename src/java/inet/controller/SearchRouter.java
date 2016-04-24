/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Admin
 */
@ManagedBean
public class SearchRouter extends BaseController {

    @Override
    public String routing() {
        return isMobile == true ? "/wap/search.xhtml" : "/web/search.xhtml";
    }

}

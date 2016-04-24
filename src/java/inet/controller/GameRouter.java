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
public class GameRouter extends BaseController {

    @Override
    public String routing() {
        System.out.println(getParameter("code"));
        System.out.println(getParameter("id"));
        return isMobile == true ? "/wap/game.xhtml" : "/web/game.xhtml";
    }

}

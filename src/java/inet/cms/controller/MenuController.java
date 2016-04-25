/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cms.controller;

import inet.controller.BaseController;
import inet.entities.Admin;
import inet.entities.Module;
import inet.util.Constants;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class MenuController extends BaseController {

    private MenuModel model;

    public MenuController() {
        model = new DefaultMenuModel();
        buildMenu();
    }

    private void buildMenu() {
        Admin admin = (Admin) getSessionValue(Constants.ADMIN);
        if(admin == null)
            return;
        List<Module> modules = admin.getModulesTree();
        for (Module item : modules) {
            if (item.getChildren().isEmpty()) {
                DefaultMenuItem element = new DefaultMenuItem(item.getName());
                item.setUrl(getRealUri() + item.getUrl());
                model.addElement(element);
            } else {
                DefaultSubMenu element = new DefaultSubMenu(item.getName());
                buildChild(element, item.getChildren());
                model.addElement(element);
            }
        }
        System.out.println(model.toString());
    }

    public MenuElement buildChild(DefaultSubMenu subMenu, List<Module> datas) {
        for (Module item : datas) {
            if (item.getChildren().isEmpty()) {
                DefaultMenuItem element = new DefaultMenuItem(item.getName());
                element.setUrl(getRealUri()+ item.getUrl());
                subMenu.addElement(element);
            } else {
                DefaultSubMenu element = new DefaultSubMenu(item.getName());
                element.addElement(buildChild(element, item.getChildren()));
                subMenu.addElement(element);
            }
        }
        return subMenu;
    }

    public MenuModel getModel() {
        return model;
    }

}

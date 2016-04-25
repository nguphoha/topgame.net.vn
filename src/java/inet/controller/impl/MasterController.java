/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.CategoryCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.entities.Category;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class MasterController extends BaseController {

    List<Category> categories;

    String categoryCode;
    String gameName;

    public MasterController() {    
        
        
        try {
            CategoryCache categoryCache = (CategoryCache) CacheFactory.getCache("category");
            categories = categoryCache.getAll();

        } catch (Exception ex) {
            logToError(ex);
        }

    }

    public void search() throws IOException {
        redirect(getContextPath() + "/tim-kiem.html?category=" + categoryCode + "&name=" + gameName);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

}

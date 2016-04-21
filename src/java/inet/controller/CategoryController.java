/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.cache.CategoryCache;
import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.common.log.Logger;
import inet.dao.GameDAO;
import inet.entities.Category;
import inet.entities.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class CategoryController extends BaseController{
    
    private String osType;
    private int catId= 0;
    private String categoryCode ;
    private String categoryName;
    
    private List<Game> games = new ArrayList();
    
    
    private Logger logger = new Logger("console");
    
    /**
     * Creates a new instance of CategoryController
     */
    public CategoryController() {
        super();
        osType = getParameter("os");
        if(osType == null)
            osType = "android";
        try{
            catId = Integer.valueOf(getParameter("catId"));
        }catch(Exception e){}
        
        categoryCode = getParameter("categoryCode");
        if(getParameter("p") != null){
            try{
                setCurrentPage(Integer.valueOf(getParameter("p")));
            }catch(Exception e){}
        }
        
        initData();
    }

    private void initData(){
        
        Category category = categoryCache.getByCode(categoryCode);
        if(category == null){
            logger.info("Get list category null ==> return");
            return;
        }
        //System.out.println("====osType ="+osType+"|catId = "+category.getId());
        categoryName = category.getName();
        try {
            games = gameCache.findByCategory(category.getId(), osType, getCurrentPage(), getPageSize());
            int count = GameDAO.getInstance().countGameByCategory(category.getId(), osType);
            pagination(count);
        } catch (Exception ex) {
            logToError("Get list game by category error: "+ex.getMessage());
        }
        
    }
    
    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import com.ocpsoft.pretty.PrettyContext;
import inet.entities.Category;
import inet.entities.Game;
import inet.entities.Seo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class MasterPageController extends BaseController {

    private String seoTitle;
    private String seoDescrption;
    private String seoKeyword;
    
    private List<Category> categories;
    
    private String requestUrl;
    
    private String gameName;
    private String osType;
    
    /**
     * Creates a new instance of MasterPageBean
     */
    public MasterPageController() {
        super();
        
        categories = categoryCache.getAll();
        initSeoTags();
    }

    private void initSeoTags(){
        PrettyContext context = PrettyContext.getCurrentInstance();
        String viewId = context.getCurrentMapping().getId();
        if("chuyen-muc".equals(viewId)){
            String categoryCode = getParameter("categoryCode");
            if(categoryCode != null){
                initSeoTags(categoryCache.getByCode(categoryCode));
            }
        }else if("chi-tiet-game".equals(viewId)){
            try{
                int gameId = Integer.valueOf(getParameter("id"));
                initSeoTags(gameCache.findById(gameId));
            }catch(Exception ex){}
        }else if("game".equals(viewId)){
            initSeoTags(seoCache.get("trang-chu"));
        }else{
            initSeoTags(seoCache.get(viewId));
        }
    }
    
    private void initSeoTags(Category category){
        if(category == null)
            return;
        seoTitle = category.getSeoTitle();
        seoKeyword = category.getSeoKeyword();
        seoDescrption = category.getSeoDescription();
    }
    
    private void initSeoTags(Game game){
        if(game == null)
            return;
        seoTitle = game.getSeoTitle();
        seoKeyword = game.getSeoKeyword();
        seoDescrption = game.getSeoDescription();
    }
    private void initSeoTags(Seo seo){
        if(seo == null)
            return;
        seoTitle = seo.getTitle();
        seoKeyword = seo.getKeyword();
        seoDescrption = seo.getDescription();
    }
    
    //backing bean action
    public void search(){
        System.out.println("========================");
        System.out.println("search action with game name = "+gameName +"|osType = "+osType);
        System.out.println("========================");
        try {
            redirect(getContextPath()+"/ket-qua-tim-kiem.html?name="+gameName+"&osType="+osType);
        } catch (IOException ex) {
            logToError("send redirect to search result controller error: "+ ex.getMessage());
        }
    }
    
    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescrption() {
        return seoDescrption;
    }

    public void setSeoDescrption(String seoDescrption) {
        this.seoDescrption = seoDescrption;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
    
    
}

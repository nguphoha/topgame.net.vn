/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import com.ocpsoft.pretty.PrettyContext;
import inet.cache.CategoryCache;
import inet.cache.GameCache;
import inet.cache.SeoCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.entities.Category;
import inet.entities.Game;
import inet.entities.Seo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author TUTL
 */
@ManagedBean
@RequestScoped
public class SeoController extends BaseController {

    private String seo = "";
    private Seo obj;
    private String avatar;
    private String uRI ;

    public SeoController() {
        try {

            uRI = PrettyContext.getCurrentInstance().getRequestURL().toURL();
            SeoCache seoCache = (SeoCache) CacheFactory.getCache("seo");
            if(seoCache == null)
                return;
            obj = seoCache.get(uRI);
            if (obj != null) {
                obj.setUrl(getRealUri() + uRI);
                seo = obj.getResult();
            } else {
                String viewId = PrettyContext.getCurrentInstance().getCurrentMapping().getId();
                if("chi-tiet".equals(viewId)){
                    //get seo theo game
                    seo = getSeoTagByGameId();
                }else if("chuyen-muc".equals(viewId)){
                    seo = getSeoTagByCategory();
                }
            }
        } catch (Exception ex) {
            logToError(ex);
        }
    }
    
    private String getSeoTagByGameId(){
        try {
            String id = getParameter("id");
            Game game = ((GameCache) CacheFactory.getCache("game")).getById(id);
            obj = new Seo();
            obj.setName(game.getName());
            obj.setTitle(game.getSeoTitle());
            obj.setKeyword(game.getSeoKeyword());
            obj.setDescription(game.getSeoDescription());
            obj.setUrl(getRealUri() + uRI);
            return obj.getResult();
        } catch (Exception ex) {
            logToError(ex);
            return "";
        }
    }
    
    private String getSeoTagByCategory(){
        try{
            String code = getParameter("code");
            Category category = ((CategoryCache) CacheFactory.getCache("category")).getByCode(code);
            obj = new Seo();
            obj.setName(category.getName());
            obj.setTitle(category.getSeoTitle());
            obj.setKeyword(category.getSeoKeyword());
            obj.setDescription(category.getSeoDescription());
            obj.setUrl(getRealUri() + uRI);
            return obj.getResult();
        }catch(Exception ex){
            logToError(ex);
            return "";
        }
    }
    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public Seo getObj() {
        return obj;
    }

    public void setObj(Seo obj) {
        this.obj = obj;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

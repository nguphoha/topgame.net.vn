/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import com.ocpsoft.pretty.PrettyContext;
import inet.cache.SeoCache;
import inet.cache.management.CacheFactory;
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

    public SeoController() {
        try {

            String uRI = PrettyContext.getCurrentInstance().getRequestURL().toURL();
            String viewId = PrettyContext.getCurrentInstance().getCurrentMapping().getId();
            SeoCache seoCache = (SeoCache) CacheFactory.getCache("Seo");
            obj = seoCache.get(uRI);
            if (obj != null) {
                obj.setUrl(getRealUri() + uRI);
                seo = obj.getResult();
            } else {

            }
        } catch (Exception ex) {
            logToError(ex);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import inet.entities.Seo;
import java.util.HashMap;
import java.util.Map;
import inet.dao.SeoDao;
import java.util.logging.Level;

/**
 *
 * @author TUTL
 */
public class SeoCache extends Cache {

    private Map<String, Seo> datas = new HashMap<String, Seo>();
    private inet.common.log.Logger error = new inet.common.log.Logger("error");

    public Seo get(String url){

        Seo seo = datas.get(url);
        synchronized (datas) {
            if (seo == null) {
                try {
                    SeoDao seoDao = new SeoDao();
                    seo = seoDao.getByURL(url);
                    if (seo != null) {
                        datas.put(url, seo);
                    }
                } catch (Exception ex) {
                    error.info("get seo by url error: " +ex.getMessage());
                }
            }
            return seo;
        }
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            datas.clear();
        }
    }

}

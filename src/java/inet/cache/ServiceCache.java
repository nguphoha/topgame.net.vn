/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import inet.dao.ServiceDao;
import inet.entities.Service;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Admin
 */
public class ServiceCache extends Cache {

    private final Map<String, Service> datas = new HashMap<String, Service>();

    public Service getByCode(String code) throws Exception {
        Service service = datas.get(code);
        if (service == null) {
            synchronized (datas) {
                service = datas.get(code);
                if (service == null) {
                    ServiceDao serviceDao = new ServiceDao();
                    service = serviceDao.getByCode(code);
                    if (service != null) {
                        datas.put(service.getCode(), service);
                    }
                }
            }
        }
        return service;
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            datas.clear();
        }
    }

}

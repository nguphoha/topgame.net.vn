/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache.management;

import inet.util.StringUtil;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author TUTL
 */
public class CacheFactory {

    public static ConcurrentHashMap<String, Cache> datas = new ConcurrentHashMap<String, Cache>();

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(CacheFactory.class.getResourceAsStream("/inet/cache/management/CacheConfiguration.properties"));
            String strKey = properties.getProperty("names"); 
            String[] keys = StringUtil.toStringArray(strKey, ",");
            for (String key : keys) {
                String strClass = properties.getProperty(key + ".class");
                Cache cache = (Cache) Class.forName(strClass).newInstance();
                long timeout = Long.parseLong(properties.getProperty(key + ".timeout"));
                cache.setTimeout(timeout * 1000);
                datas.put(key, cache);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Cache getCache(String name) {
        return datas.get(name);
    }
}

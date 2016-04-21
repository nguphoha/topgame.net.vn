/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache.management;

import inet.util.Constants;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author TUTL
 */
public class CacheManagement extends Thread {

    @Override
    public void run() {
        while (Constants.isRunning) {
            try {
                synchronized (CacheFactory.datas) {
                    for (Map.Entry<String, Cache> entry : CacheFactory.datas.entrySet()) {
                        Cache cache = entry.getValue();
                        if ((System.currentTimeMillis() - cache.getClearDate().getTime()) >= cache.getTimeout()) {
                            cache.clearCache();
                            cache.setClearDate(new Timestamp(System.currentTimeMillis()));
                        }
                    }
                }
                sleep(10, 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sleep(int seconds, int inteval) throws InterruptedException {
        for (int Index = seconds; Index > 0; Index--) {
            Thread.sleep(inteval);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.listener;

import inet.cache.management.CacheManagement;
import inet.common.database.pool.DBPool;
import inet.common.log.Logger;
import inet.util.Constants;
import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ApplicationServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Constants.isRunning = true;
            CacheManagement management = new CacheManagement();
            management.start();
            Logger.setLogDir(sce.getServletContext().getRealPath("/") + "logs" + File.separator);
            DBPool.loadConfiguration(sce.getServletContext().getResourceAsStream("/WEB-INF/dbpool.properties"));
        } catch (Exception ex) {
            Logger logger = new Logger("error");
            logger.info(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Constants.isRunning = false;
            DBPool.releaseAll();
        } catch (Exception ex) {
            Logger logger = new Logger("error");
            logger.info(ex);
        }
    }
}

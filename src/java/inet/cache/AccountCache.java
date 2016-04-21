/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import inet.dao.AccountDao;
import inet.entities.Account;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AccountCache extends Cache{

    private Map<String, Account> datas = new HashMap<String, Account>();
    
    public Account get(String mobile) throws Exception{
        Account acc = datas.get(mobile);
        synchronized (datas) {
            if (acc == null) {
                AccountDao seoDao = new AccountDao();
                acc = seoDao.getByMobile(mobile);
                if (acc != null) {
                    datas.put(mobile, acc);
                }
                
            }
            return acc;
        }
    }
    
    @Override
    public void clearCache() {
        synchronized(this){
            datas.clear();
        }
    }
    
}

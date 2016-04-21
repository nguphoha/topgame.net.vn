/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache.management;

import java.sql.Timestamp;

/**
 *
 * @author TUTL
 */
public abstract class Cache {

    private Timestamp clearDate;
    private long timeout;

    public Cache() {
        clearDate = new Timestamp(System.currentTimeMillis());
    }

    public abstract void clearCache();

    public Timestamp getClearDate() {
        return clearDate;
    }

    public void setClearDate(Timestamp clearDate) {
        this.clearDate = clearDate;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}

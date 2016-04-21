/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.dao.AccountDao;
import inet.util.Constants;
import inet.util.StringUtil;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
@Table(name = "account")
public class Account {

    public static final String PENDING = "0";
    public static final String ACTIVE = "1";
    public static final String CANCEL = "-1";

    public static final String CHANNEL_WAP = "WAP";
    public static final String CHANNEL_SMS = "SMS";

    public static final Object sync = new Object();

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    String status;

    @Column(name = "date_create")
    Timestamp dateCreate;

    @Column(name = "aes_key")
    String key;

    Map<String, AccountService> services = new HashMap<String, AccountService>();

    public Account insert() throws Exception {
        synchronized (sync) {
            AccountDao dao = new AccountDao();
            this.id = dao.getSequenceValue("account", Constants.DATABASE);
            dao.insert(this);
        }
        return this;
    }

    public Account update() throws Exception {
        AccountDao dao = new AccountDao();
        dao.update(this);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }
    public String getDateCreateString() {
        return dateCreate== null ? "" : StringUtil.format(new Date(dateCreate.getTime()), "dd/MM/yyyy");
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, AccountService> getServices() {
        return services;
    }

    public void setServices(Map<String, AccountService> services) {
        this.services = services;
    }

    public void putService(String key, AccountService value) {
        services.put(key, value);
    }

    public AccountService getService(String key) {
        return services.get(key);
    }
}

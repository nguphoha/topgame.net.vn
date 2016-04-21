/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.dao.AccountServiceDao;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
@Table(name = "account_service")
public class AccountService {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "account_id")
    String accountId;

    @Column(name = "service_id")
    String serviceId;

    @Column(name = "service_code")
    String serviceCode;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "date_create")
    Timestamp dateCreate;

    @Column(name = "date_latest_create")
    Timestamp dateLatestCreate;

    @Column(name = "date_renew")
    Timestamp dateRenew;

    @Column(name = "date_cancel")
    Timestamp dateCancel;

    @Column(name = "date_expire")
    Timestamp dateExpire;

    @Column(name = "partner")
    String partner;

    @Column(name = "url_register")
    String urlRegister;

    @Column(name = "status")
    String status;

    @Column(name = "properties")
    String properties;

    @Column(name = "channel")
    private String channel;

    @Column(name = "charge_sequence")
    private int chargeSequence;

    public AccountService insert() throws Exception {
        AccountServiceDao dao = new AccountServiceDao();
        dao.insert(this);
        return this;
    }

    public AccountService update() throws Exception {
        AccountServiceDao dao = new AccountServiceDao();
        dao.update(this);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateRenew() {
        return dateRenew;
    }

    public void setDateRenew(Timestamp dateRenew) {
        this.dateRenew = dateRenew;
    }

    public Timestamp getDateCancel() {
        return dateCancel;
    }

    public void setDateCancel(Timestamp dateCancel) {
        this.dateCancel = dateCancel;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getUrlRegister() {
        return urlRegister;
    }

    public void setUrlRegister(String urlRegister) {
        this.urlRegister = urlRegister;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Timestamp getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Timestamp dateExpire) {
        this.dateExpire = dateExpire;
    }

    public Timestamp getDateLatestCreate() {
        return dateLatestCreate;
    }

    public void setDateLatestCreate(Timestamp dateLatestCreate) {
        this.dateLatestCreate = dateLatestCreate;
    }

    public int getChargeSequence() {
        return chargeSequence;
    }

    public void setChargeSequence(int chargeSequence) {
        this.chargeSequence = chargeSequence;
    }

}

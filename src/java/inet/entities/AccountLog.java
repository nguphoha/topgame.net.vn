/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import java.sql.Timestamp;
import inet.dao.AccountLogDao;

/**
 *
 * @author Admin
 */
@Table(name = "account_log")
public class AccountLog {

    public static final String SUCCESS = "0";
    public static final String FAIL = "1";
    public static final String TYPE_REGISTER = "REGISTER";
    public static final String TYPE_MONFEE = "MONFEE";
    public static final String TYPE_CANCEL = "CANCEL";

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

    @Column(name = "type")
    String type;

    @Column(name = "partner")
    String partner;

    @Column(name = "amount")
    String amount;

    @Column(name = "result")
    String result;

    @Column(name = "date_create")
    Timestamp dateCreate;

    public void insert() throws Exception {
        AccountLogDao dao = new AccountLogDao();
        dao.insert(this);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

}

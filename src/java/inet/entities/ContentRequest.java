/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

/**
 *
 * @author TOM
 */
public class ContentRequest {

    public String username;
    public String password;
    public String serviceId;
    public String msisdn;
    public String params;
    public String mode;
    public String amount;

    public ContentRequest(String username, String password, String serviceId, String msisdn, String params, String mode, String amount) {
        this.username = username;
        this.password = password;
        this.serviceId = serviceId;
        this.msisdn = msisdn;
        this.params = params;
        this.mode = mode;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
@Table(name = "mo_log")
public class MOLog {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "service_number")
    String serviceNumber;

    @Column(name = "command_code")
    String commandCode;

    @Column(name = "content")
    String content;

    @Column(name = "date_create")
    Timestamp dateCreate;

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

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

}

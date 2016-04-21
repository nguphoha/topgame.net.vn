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
@Table(name = "service")
public class Service {

    @Column(name = "id", PK = true)
    String id;

    @Column(name = "name")
    String name;
    @Column(name = "code")

    String code;

    @Column(name = "price")
    int price;

    @Column(name = "charge_cycle")
    int chargeCycle;

    @Column(name = "date_create")
    Timestamp dateCreate;

    @Column(name = "status")
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getChargeCycle() {
        return chargeCycle;
    }

    public void setChargeCycle(int chargeCycle) {
        this.chargeCycle = chargeCycle;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

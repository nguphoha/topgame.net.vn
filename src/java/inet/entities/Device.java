/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import java.io.Serializable;

/**
 *
 * @author TOM
 */
public class Device implements Serializable {

    String id;
    String manufacturer;
    String model;
    String useragent;
    String diplay;
    String cldc;

    public Device(String id, String manufacturer, String model, String useragent, String diplay, String cldc) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.useragent = useragent;
        this.diplay = diplay;
        this.cldc = cldc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getDiplay() {
        return diplay;
    }

    public void setDiplay(String diplay) {
        this.diplay = diplay;
    }

    public String getCldc() {
        return cldc;
    }

    public void setCldc(String cldc) {
        this.cldc = cldc;
    }

}

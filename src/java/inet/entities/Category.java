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
@Table(name = "category")
public class Category {

    public static int ALL = -1;
    public static int INACTIVE = 0;
    public static int ACTIVE = 1;
    
    @Column(name = "id", PK = true)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "date_create")
    Timestamp dateCreate;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "status")
    String status;

    @Column(name = "seo_title")
    String seoTitle;

    @Column(name = "seo_keyword")
    String seoKeyword;

    @Column(name = "seo_description")
    String seoDescription;
    
    int totalGame;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seo_description) {
        this.seoDescription = seo_description;
    }

    public int getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", code=" + code + ", dateCreate=" + dateCreate + ", avatar=" + avatar + ", status=" + status + ", seoTitle=" + seoTitle + ", seoKeyword=" + seoKeyword + ", seoDescription=" + seoDescription + '}';
    }
    
}

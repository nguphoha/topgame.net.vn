/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.util.StringUtil;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Admin
 */
@Table(name = "game")
public class Game {

    public static int ALL = -1;
    public static int INACTIVE = 0;
    public static int ACTIVE = 1;
    
    public static String GAME_HOT = "GAME_HOT";
    public static String GAME_NEWEST = "GAME_NEWEST";
    public static String GAME_MOST_VIEW = "GAME_MOST_VIEW";
    public static String GAME_MOST_DOWNLOAD = "GAME_MOST_DOWNLOAD";
    
    public static final int HOT = 1;
    public static final int NO_HOT = 0;

    @Column(name = "id", PK = true)
    String id;
    
    @Column(name = "category_id")
    String categoryId;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "author")
    String author;

    @Column(name = "description")
    String description;

    @Column(name = "view_count")
    int viewCount;

    @Column(name = "download_count")
    int downloadCount;

    @Column(name = "date_create")
    Timestamp dateCreate;

    @Column(name = "status")
    String status;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "seo_title")
    String seoTitle;

    @Column(name = "seo_description")
    String seoDescription;

    @Column(name = "seo_keyword")
    String seoKeyword;

    @Column(name = "hot")
    int hot;
    
    @Column(name = "download_url")
    String downloadUrl;
    
    String categoryName;
    String categoryCode;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }
    
    public String getDateCreateString() {
        return dateCreate == null ?"" : StringUtil.format(new Date(dateCreate.getTime()), "dd/MM/yyyy hh:mm:ss aaa");
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    
    @Override
    public String toString() {
        return "Game{" + "id=" + id + ", name=" + name + ", code=" + code + ", author=" + author + ", des=" + description + ", viewCount=" + viewCount + ", downloadCount=" + downloadCount + ", dateCreate=" + dateCreate + ", status=" + status + ", avatar=" + avatar + ", seoTitle=" + seoTitle + ", seoDescription=" + seoDescription + ", seoKeyword=" + seoKeyword + ", hot=" + hot + '}';
    }
    
}

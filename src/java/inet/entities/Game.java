/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.cache.CategoryCache;
import inet.cache.management.CacheFactory;
import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.dao.GameOSDao;
import inet.util.StringUtil;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
@Table(name = "game")
public class Game implements Serializable{

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

    Map<String, GameOS> os = new HashMap<String, GameOS>();
    String url;

    public Category getCategory() throws Exception {
        CategoryCache categoryCache = (CategoryCache) CacheFactory.getCache("category");
        return categoryCache.getById(Integer.parseInt(categoryId));
    }

    public String getUrl() {
        if (url == null) {
            return StringUtil
                    .standardized(StringUtil.clearUnicode(name.toLowerCase()))
                    .replace(" ", "-")
                    .replaceAll("[+.^:,]","") + "-" + id + ".html";
        }
        return url;
    }

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, GameOS> getOs() {
        return os;
    }

    public void setOs(Map<String, GameOS> os) {
        this.os = os;
    }

    public GameOS getGameOS(String key) {
        return os.get(key);
    }

    public void putGameOS(String key, GameOS gameOS) {
        os.put(key, gameOS);
    }

    public void loadGameOS() throws Exception {
        GameOSDao gameOSDao = new GameOSDao();
        os = gameOSDao.getByGameID(id);
    }

}

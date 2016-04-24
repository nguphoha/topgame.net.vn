/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.CategoryCache;
import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.entities.Category;
import inet.entities.Game;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class CategoryController extends BaseController {

    List<Game> games;
    String code;
    String name;

    public CategoryController() {

        pageSize = (isMobile ? 15 : 24);

        GameCache cache = (GameCache) CacheFactory.getCache("game");
        curentPage = Integer.parseInt(getParameter("page", "1"));

        try {
            code = getParameter("code");
            if (code.equals("game-moi-nhat")) {

                games = cache.findByCategory(Category.NEW, null, (curentPage * pageSize) - pageSize, pageSize);
                pagination(cache.count(Category.NEW, null));
                name = "MỚI NHẤT";

            } else if (code.equals("game-hot-nhat")) {

                games = cache.findByCategory(Category.HOT, null, (curentPage * pageSize) - pageSize, pageSize);
                pagination(cache.count(Category.HOT, null));
                name = "HOT NHẤT";

            } else if (code.equals("game-xem-nhieu-nhat")) {

                games = cache.findByCategory(Category.VIEW, null, (curentPage * pageSize) - pageSize, pageSize);
                pagination(cache.count(Category.VIEW, null));
                name = "XEM NHIỀU NHẤT";

            } else {
                Category category = ((CategoryCache) CacheFactory.getCache("category")).getByCode(code);
                if (category == null) {
                    redirect(getRealPath());
                } else {
                    games = cache.findByCategory(category.getId(), null, (curentPage * pageSize) - pageSize, pageSize);
                    pagination(cache.count(category.getId(), null));
                    name = category.getName();
                }
            }
        } catch (Exception ex) {
            logToError(ex);
        }

    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

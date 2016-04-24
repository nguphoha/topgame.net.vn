/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.CategoryCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.dao.GameDAO;
import inet.entities.Category;
import inet.entities.Game;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author TOM
 */
@ManagedBean
@RequestScoped
public class SearchController extends BaseController {

    List<Game> games;

    public SearchController() {

        curentPage = Integer.parseInt(getParameter("page", "1"));
        pageSize = (isMobile ? 15 : 24);

        String name = getParameter("name");
        String code = getParameter("category");

        try {

            CategoryCache cache = (CategoryCache) CacheFactory.getCache("category");
            Category category = cache.getByCode(code);

            if (category == null) {
                GameDAO dao = new GameDAO();
                games = dao.findByNameAndCategory(name, null, (curentPage * pageSize) - pageSize, pageSize);
            } else {
                GameDAO dao = new GameDAO();
                games = dao.findByNameAndCategory(name, String.valueOf(category.getId()), (curentPage * pageSize) - pageSize, pageSize);
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

}

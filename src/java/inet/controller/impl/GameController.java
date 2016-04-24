/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.dao.GameDAO;
import inet.entities.Game;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class GameController extends BaseController {

    List<Game> games;
    Game game;

    public GameController() {

        try {
            String id = getParameter("id");
            game = ((GameCache) CacheFactory.getCache("game")).getById(id);
            
            //tăng lượt view
            (new GameDAO()).increaseViewCount(id);
            
            games = ((GameCache) CacheFactory.getCache("game")).findByCategory(game.getCategory().getId(), null, 0, 9);

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}

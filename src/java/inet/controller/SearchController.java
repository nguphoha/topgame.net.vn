/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.cache.AccountCache;
import inet.cache.management.CacheFactory;
import inet.dao.AccountDao;
import inet.dao.GameDAO;
import inet.entities.Account;
import inet.entities.Game;
import inet.util.Constants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class SearchController extends BaseController{

    private String name, osType;
    
    private List<Game> games;
    
    /**
     * Creates a new instance of LoginController
     */
    public SearchController() {
        super();
        init();
    }

    private void init(){
        name = getParameter("name");
        osType = getParameter("osType");
        try {
            games = GameDAO.getInstance().findByName(name, osType);
        } catch (Exception ex) {
            logToError("Find games by name and osType error "+ex.getMessage());
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    
    
}

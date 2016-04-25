/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
import inet.entities.Account;
import inet.entities.AccountService;
import inet.entities.Game;
import inet.util.Constants;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class ProfileController  extends BaseController {

    private Account profile;
    
    private AccountService service;
    
    private List<Game> games;
    /**
     * Creates a new instance of ProfileController
     */
    public ProfileController() {
        super();
        profile = (Account) getSessionValue(Constants.ACCOUNT);
        
        if(profile == null){
            try {
                redirect(getRealUri());
            } catch (IOException ex) {
                logToError(ex);
            }
        }
        service = profile.getService(Constants.SERVICE_CODE);
        try {
            curentPage = Integer.parseInt(getParameter("page", "1"));
            GameCache cache = (GameCache)CacheFactory.getCache("game");
            games = cache.findDownloadHistory(Integer.valueOf(profile.getId())
                    , curentPage, pageSize);
            pagination(cache.countDownloadHistory(Integer.valueOf(profile.getId())));
        } catch (Exception ex) {
            logToError(ex);
        }
    }

    public Account getProfile() {
        return profile;
    }

    public void setProfile(Account profile) {
        this.profile = profile;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public AccountService getService() {
        return service;
    }

    public void setService(AccountService service) {
        this.service = service;
    }

   
    
}

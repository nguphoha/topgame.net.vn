/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller.impl;

import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.controller.BaseController;
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
public class IndexController extends BaseController {

    List<Game> hotes;
    List<Game> newest;
    List<Game> viewest;

    public IndexController() {

        try {

            GameCache gameCache = (GameCache) CacheFactory.getCache("game");
            newest = gameCache.get("NEW");
            hotes = gameCache.get("HOT");
            viewest = gameCache.get("VIEW");

        } catch (Exception ex) {
            logToError(ex);
        }

    }

    public List<Game> getHotes() {
        return hotes;
    }

    public void setHotes(List<Game> hotes) {
        this.hotes = hotes;
    }

    public List<Game> getNewest() {
        return newest;
    }

    public void setNewest(List<Game> newest) {
        this.newest = newest;
    }

    public List<Game> getViewest() {
        return viewest;
    }

    public void setViewest(List<Game> viewest) {
        this.viewest = viewest;
    }

}

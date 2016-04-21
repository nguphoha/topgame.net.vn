/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.cache.GameCache;
import inet.cache.management.CacheFactory;
import inet.constant.Constant;
import inet.dao.GameDAO;
import inet.entities.Game;
import inet.entities.Pagination;
import java.util.ArrayList;
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
public class HomeController extends BaseController{

    private List<Game> gamesNewest = new ArrayList();
    private List<Game> gamesMostView = new ArrayList();
    private List<Game> gamesMostDownload = new ArrayList();
    private List<Game> gamesHOT = new ArrayList();
    
    private int countGameHOT = 0;
    private int countGameMostView = 0;
    private int countGameMostDownload = 0;
    private int countGameNewest = 0;    
    
    private int categoryId = 0;    
    
    private boolean isShowPaging = false;
    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
        super();
        init();
    }

    private void init(){
//        if(getParameter("action") != null && "view-more".equals(getParameter("action"))){
//            isShowPaging = true;
//        }
        isShowPaging = getParameter("typeView") != null;
//        System.out.println("=========="+ getParameter("typeView"));
        if(getParameter("p") != null){
            try{
                setCurrentPage(Integer.valueOf(getParameter("p")));
            }catch(Exception e){
                logToError("init home controller error: "+ e.getMessage());
            }
        }        
    }
    
    public List<Game> getGamesNewest() {
        if(gamesNewest.isEmpty()){
            try {
                gamesNewest = gameCache.find(Game.GAME_NEWEST,categoryId, getCurrentPage(), getPageSize());
                if(isShowPaging){
                    int count = GameDAO.getInstance().countGame();
                    pagination(count);
                }
            } catch (Exception ex) {
                logToError("Get list game newest error: "+ex.getMessage());
            }
        }
        return gamesNewest;
    }

    public void setGamesNewest(List<Game> gamesNewest) {
        this.gamesNewest = gamesNewest;
    }

    public List<Game> getGamesMostView() {
        if(gamesMostView.isEmpty()){
            try {
                gamesMostView = gameCache.find(Game.GAME_MOST_VIEW,categoryId,getCurrentPage(), getPageSize());
                if(isShowPaging){
                    int count = GameDAO.getInstance().countGame();
                    pagination(count);
                }
            } catch (Exception ex) {
                logToError("Get list game most view error: "+ex.getMessage());
            }
        }
        return gamesMostView;
    }

    public void setGamesMostView(List<Game> gamesMostView) {
        this.gamesMostView = gamesMostView;
    }

    public List<Game> getGamesMostDownload() {
        if(gamesMostDownload.isEmpty()){
            try {
                gamesMostDownload = gameCache.find(Game.GAME_MOST_DOWNLOAD,categoryId,getCurrentPage(), getPageSize());
                if(isShowPaging){
                    int count = GameDAO.getInstance().countGame();
                    pagination(count);
                }
            } catch (Exception ex) {
               logToError("Get list game most download error: "+ex.getMessage());
            }
        }
        return gamesMostDownload;
    }

    public void setGamesMostDownload(List<Game> gamesMostDownload) {
        this.gamesMostDownload = gamesMostDownload;
    }

    public List<Game> getGamesHOT() {
        if(gamesHOT.isEmpty()){ 
            try {
                gamesHOT = gameCache.find(Game.GAME_HOT,categoryId, getCurrentPage(), getPageSize());
                if(isShowPaging){
                    int count = GameDAO.getInstance().countGameHot();
                    pagination(count);
                }
            } catch (Exception ex) {
                logToError("Get list game HOT error: "+ex.getMessage());
            }
        }
        return gamesHOT;
    }

    public void setGamesHOT(List<Game> gamesHOT) {
        this.gamesHOT = gamesHOT;
    }

    public int getCountGameHOT() {
        return countGameHOT;
    }

    public void setCountGameHOT(int countGameHOT) {
        this.countGameHOT = countGameHOT;
    }

    public int getCountGameMostView() {
        return countGameMostView;
    }

    public void setCountGameMostView(int countGameMostView) {
        this.countGameMostView = countGameMostView;
    }

    public int getCountGameMostDownload() {
        return countGameMostDownload;
    }

    public void setCountGameMostDownload(int countGameMostDownload) {
        this.countGameMostDownload = countGameMostDownload;
    }

    public int getCountGameNewest() {
        return countGameNewest;
    }

    public void setCountGameNewest(int countGameNewest) {
        this.countGameNewest = countGameNewest;
    }

    
    
}

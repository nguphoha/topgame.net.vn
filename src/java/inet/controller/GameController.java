/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.controller;

import inet.entities.Account;
import inet.entities.Game;
import inet.util.Constants;
import inet.util.Encrypter;
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
public class GameController extends BaseController{
    
    private String gameCode;
    
    private String gameId;
    
    private Game game;
    
    private List<Game> gamesRelative = new ArrayList();
    
    /**
     * Creates a new instance of GameController
     */
    public GameController() {
        super();
        initPage();
    }
    
    private void initPage(){
        gameId = getParameter("id");
        if(gameId == null)
            return;
        
        try {
            game = gameCache.findById(Integer.valueOf(gameId));
        } catch (Exception ex) {
            logToError("Find game by id " + gameId + " error "+ex.getMessage());
        }
        if(game == null)
            return;
        try {
            gamesRelative = gameCache.findByCategory(Integer.valueOf(game.getCategoryId()), "", getCurrentPage(), getPageSize());
        } catch (Exception ex) {
            logToError("Find list game relate game id " + gameId + " error "+ex.getMessage());
        }
    }

    public void register(){
        System.out.println("================");
        System.out.println("register to download game");
        System.out.println("================");
    }
    
    public void processDownloadGame(String encodeUrl){
        try {
            Account account = (Account) getSessionValue(Constants.ACCOUNT);
            if(account == null)
                return;
            
            System.out.println("================");
            System.out.println("processDownloadGame encodeUrl "+encodeUrl);
            System.out.println("processDownloadGame origin url  "+Encrypter.decrypt(encodeUrl, account.getKey()));
            System.out.println("================");
        } catch (Exception ex) {
            logToError("Decrypt url "+encodeUrl +" with key error "+ex.getMessage());
        }
    }
    
    public String getDownloadUrl(){
        if(game == null)
            return "";
        
        Account account = (Account) getSessionValue(Constants.ACCOUNT);
        if(account == null)
            return "";
        
        try {
            return Encrypter.encrypt(game.getDownloadUrl(), account.getKey());
        } catch (Exception ex) {
            logToError("Encrypt url "+game.getDownloadUrl() +" with key "+account.getKey() +" error "+ex.getMessage());
            return "";
        }
    }
    
    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Game> getGamesRelative() {
        return gamesRelative;
    }

    public void setGamesRelative(List<Game> gamesRelative) {
        this.gamesRelative = gamesRelative;
    }
    
    
}

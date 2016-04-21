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
import java.io.IOException;
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
public class ProfileController extends BaseController{

    private Account account;
    
    private List<Game> gamesDownloaded  = new ArrayList();
    
    /**
     * Creates a new instance of LoginController
     */
    public ProfileController() {
        super();
        init();
    }

    private void init(){
        account = (Account)getSessionValue(Constants.ACCOUNT);
        if(account == null){
            try {
                redirect(getContextPath() +"/dang-nhap.html");
            } catch (IOException ex) {
                logToError("redirect to login page error: " +ex.getMessage());
            }
            return;
        }
        try {
            if(!getParameter("p").equals(""))
                setCurrentPage(Integer.valueOf(getParameter("p")));
            
            gamesDownloaded = gameCache.findDownloadHistory(Integer.valueOf(account.getId()),getCurrentPage(), getPageSize());
            int count = GameDAO.getInstance().countDownloadHistory(Integer.valueOf(account.getId()));
            pagination(count);
        } catch (Exception ex) {
            logToError("Get list game download of account "+account.getMobile()+" error "+ex.getMessage());
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Game> getGamesDownloaded() {
        return gamesDownloaded;
    }

    public void setGamesDownloaded(List<Game> gamesDownloaded) {
        this.gamesDownloaded = gamesDownloaded;
    }
    
    
}

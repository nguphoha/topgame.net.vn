/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import inet.dao.GameDAO;
import inet.entities.Game;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Admin
 */
public class GameCache extends Cache {

    private static Map<String, Game> hmGame = new ConcurrentHashMap<String, Game>();
    private static Map<String, List<Game>> hmGames = new ConcurrentHashMap<String, List<Game>>();
    private static Map<String, Integer> hmCount = new ConcurrentHashMap<String, Integer>();

    public List<Game> get(String key) throws Exception {

        List<Game> games = hmGames.get(key);
        synchronized (hmGames) {
            if (games == null) {
                GameDAO dao = new GameDAO();
                if (key.equals("HOT")) {
                    games = dao.findHot(9, 0);
                } else if (key.equals("NEW")) {
                    games = dao.findNewest(4, 0);
                } else {
                    games = dao.findViewest(9, 0);
                }
                hmGames.put(key, games);
            }
        }
        return games;
    }

    public List<Game> findByCategory(int catId, String os, int offset, int limit) throws Exception {

        String key = buildKeyGames(catId, os, offset, limit);
        List<Game> games = hmGames.get(key);

        synchronized (hmGames) {
            if (games == null) {
                GameDAO dao = new GameDAO();
                games = dao.findByCategory(catId, os, offset, limit);
                if (!games.isEmpty()) {
                    hmGames.put(key, games);
                    for (Game g : games) {
                        hmGame.put(g.getId(), g);
                    }
                }
            }
            return games;
        }
    }
    public List<Game> findDownloadHistory(int accountId,int page, int pageSize)  throws Exception {
        String key = buildKeyGamesDownload(accountId, page,pageSize);
        List<Game> games = hmGames.get(key);
        synchronized (hmGames) {
            if (games == null) {
                games = new GameDAO().findDownloadHistory(accountId, page, pageSize);
                if (games != null && !games.isEmpty()) {
                    hmGames.put(key, games);
                }
            }
            return games;
        }
    }
    private String buildKeyGamesDownload(int accountId, int page,int pageSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("download").append(accountId).append("_page_")
                .append(page).append("_pageSize_").append(pageSize);
        return sb.toString();
    }
    public int count(int catId, String os) throws Exception {

        String key = catId + "_" + os + "COUNT";
        Integer total = hmCount.get(key);

        synchronized (hmCount) {
            if (total == null) {
                GameDAO dao = new GameDAO();
                total = dao.countGameByCategory(catId, os);
                hmCount.put(key, total);
            }
            return total;
        }
    }
    
     public int countDownloadHistory(int accountId) throws Exception{
         String key =  "account_" + accountId + "_COUNT";
        Integer total = hmCount.get(key);

        synchronized (hmCount) {
            if (total == null) {
                GameDAO dao = new GameDAO();
                total = dao.countDownloadHistory(accountId);
                hmCount.put(key, total);
            }
            return total;
        }
     }
    private String buildKeyGames(int catId, String os, int page, int pageSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("category").append(catId).append(os).append("_page_")
                .append(page);
        return sb.toString();
    }

    public Game getById(String id) throws Exception {

        Game game = hmGame.get(id);

        synchronized (hmGame) {
            if (game == null) {
                GameDAO dao = new GameDAO();
                game = dao.findById(id);
                if (game != null) {
                    hmGame.put(id, game);
                }
            }
        }
        return game;
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            hmGames.clear();
            hmGame.clear();
            hmCount.clear();
        }
    }
}

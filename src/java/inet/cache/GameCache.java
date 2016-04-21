/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.cache;

import inet.cache.management.Cache;
import inet.dao.GameDAO;
import inet.entities.Game;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class GameCache extends Cache {

    private Map<String, Game> hmGame = new HashMap<String, Game>();
    private Map<String, List<Game>> hmGames = new HashMap<String, List<Game>>();

    public List<Game> findByCategory(int catId, String os, int page, int pageSize) throws Exception {
        String key = buildKeyGames(catId, os, page, pageSize);
        List<Game> games = hmGames.get(key);
        synchronized (hmGames) {
            if (games == null) {
                games = GameDAO.getInstance().findByCategory(catId, os, page, pageSize);
                if (games != null && !games.isEmpty()) {
                    hmGames.put(key, games);
                    String keyGame;
                    for (Game g : games) {
                        keyGame = buildKeyGame(g.getId());
                        if (!hmGame.containsKey(keyGame)) {
                            hmGame.put(keyGame, g);
                        }
                    }
                }
            }
            return games;
        }
    }

    public Game findById(int id) throws Exception {
        String key = buildKeyGame(id + "");
        Game game = hmGame.get(key);
        synchronized (hmGame) {
            if (game == null) {
                game = GameDAO.getInstance().findById(id);
                if (game != null) {
                    hmGame.put(key, game);
                }
            }
            return game;
        }
    }

    public List<Game> findDownloadHistory(int accountId,int page, int pageSize)  throws Exception {
        String key = buildKeyGamesDownload(accountId, page);
        List<Game> games = hmGames.get(key);
        synchronized (hmGames) {
            if (games == null) {
                games = GameDAO.getInstance().findDownloadHistory(accountId, page, pageSize);
                if (games != null && !games.isEmpty()) {
                    hmGames.put(key, games);
                }
            }
            return games;
        }
    }
    
    public List<Game> find(String typeView, int catId, int page, int pageSize) throws Exception {
        String key = typeView + buildKeyGames(catId, "", page, pageSize);
        List<Game> games = hmGames.get(key);
        synchronized (hmGames) {
            if (games == null) {
                games = loadGameFromDB(typeView, catId, page, pageSize);
                if (games != null && !games.isEmpty()) {
                    hmGames.put(key, games);
                    String keyGame;
                    for (Game g : games) {
                        keyGame = buildKeyGame(g.getId());
                        if (!hmGame.containsKey(keyGame)) {
                            hmGame.put(keyGame, g);
                        }
                    }
                }
            }
            return games;
        }
    }

    private List<Game> loadGameFromDB(String typeView, int catId, int page, int pageSize) throws Exception {
        List<Game> games = null;
        if (Game.GAME_HOT.equals(typeView)) {
            games = GameDAO.getInstance().findGameHot(catId, page, pageSize);
        }else if(Game.GAME_MOST_VIEW.equals(typeView)){
            games = GameDAO.getInstance().findGameMostView(catId, page, pageSize);
        }else if(Game.GAME_NEWEST.equals(typeView)){
            games = GameDAO.getInstance().findGameNewest(catId, page, pageSize);
        }else if(Game.GAME_MOST_DOWNLOAD.equals(typeView)){
            games = GameDAO.getInstance().findGameMostDownload(catId, page, pageSize);
        }
        return games;
    }

    private String buildKeyGames(int catId, String os, int page, int pageSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("category").append(catId).append(os).append("_page_")
                .append(page);
        return sb.toString();
    }
    private String buildKeyGamesDownload(int accountId, int page) {
        StringBuilder sb = new StringBuilder();
        sb.append("download").append(accountId).append("_page_")
                .append(page);
        return sb.toString();
    }

    private String buildKeyGame(String id) {
        StringBuilder sb = new StringBuilder();
        return sb.append("game_id_").append(id).toString();
    }

    @Override
    public void clearCache() {
        synchronized (this) {
            hmGames.clear();
            hmGame.clear();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import com.ocpsoft.pretty.faces.util.StringUtils;
import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.constant.Constant;
import inet.entities.Category;
import inet.entities.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TUTL
 */
public class GameDAO extends AbstractDAO {

   
    private static GameDAO _instance = null;
    
    public static GameDAO getInstance()  throws Exception{
        if(_instance == null)
            _instance = new GameDAO();
        return _instance;
        
    }
    
    private RowMapper<Game> rowMapper;

    private GameDAO() throws Exception {
        super(Constant.POOL_NAME_DEFAULT);

        rowMapper = new RowMapper<Game>() {
            Game game;

            @Override
            public Game map(ResultSet rs) throws SQLException {
                game = new Game();
                
                game.setId(rs.getString("id"));
                game.setCategoryId(rs.getString("category_id"));
                game.setName(rs.getString("name"));
                game.setCode(rs.getString("code"));
                game.setAuthor(rs.getString("author"));
                game.setDescription(rs.getString("description"));
                try{
                    game.setStatus(rs.getString("status"));
                }catch(Exception e){}
                game.setDateCreate(rs.getTimestamp("date_create"));
                game.setAvatar(rs.getString("avatar"));
                try{
                    game.setSeoTitle(rs.getString("seo_title"));
                }catch(Exception e){}
                try{
                    game.setSeoKeyword(rs.getString("seo_keyword"));
                }catch(Exception e){}
                try{
                    game.setSeoDescription(rs.getString("seo_description"));
                }catch(Exception e){}
                
                game.setViewCount(rs.getInt("view_count"));
                game.setDownloadCount(rs.getInt("download_count"));
                game.setHot(rs.getInt("hot"));
                try{
                    game.setCategoryName(rs.getString("category_name"));
                }catch(Exception e){}
                try{
                    game.setCategoryCode(rs.getString("category_code"));
                }catch(Exception e){}
                return game;
            }
        };
    }

    /**
     * Find all game
     * @param status
     * @return 
     */
    public List<Game> find(int status) throws Exception{
        String sql = "Select * from Game ";
        List params = new ArrayList();
        if(status != Game.ALL){
            sql += " where status = ?";
            params.add(status);
        }
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games;
    }

    public Game findById(int id)  throws Exception{
       String sql = "SELECT G.*,C.name category_name,C.code category_code\n"
                + " FROM game G\n"
                + "LEFT JOIN category C ON G.`category_id` = C.`id`\n"
                +" WHERE G.status = "+Game.ACTIVE +" AND G.id = ?";
        List params = new ArrayList();
        params.add(id);
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games.get(0);
    }
    
    /**
     * find game HOT theo category
     * @param catId
     * @param page
     * @param pageSize
     * @return 
     */
    public List<Game> findGameHot(int catId, int page, int pageSize) throws Exception{
        String sql = "SELECT distinct G.*,C.name category_name,C.code category_code\n"
                + " FROM game G\n"
                + "LEFT JOIN category C ON G.`category_id` = C.`id`\n"
                +" WHERE G.hot = " + Game.HOT + " AND G.status = "+Game.ACTIVE;
        List params = new ArrayList();
        if(catId > 0){
            sql += " AND C.id = ?";
            params.add(catId);            
        }
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public int countGameHot() throws Exception{
        String sql = "SELECT count(G.id) count_game\n"
                + " FROM game G\n"
                +" WHERE G.hot = " + Game.HOT + " AND G.status = "+Game.ACTIVE;
        List params = new ArrayList();
        return  executeQueryCountGame(sql, params);            
        
    }
    
    public List<Game> findDownloadHistory(int accountId,int page, int pageSize) throws Exception{
        String sql = "SELECT G.*, C.name category_name, C.code category_code "
                + " FROM game G\n"
                + " INNER JOIN download_history DH on DH.game_id = G.id"
                + " INNER JOIN category C on C.id = G.category_id"
                + " WHERE DH.account_id = ? order by DH.date_create desc ";
        List params  = new ArrayList();
        params.add(accountId);
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public int countDownloadHistory(int accountId) throws Exception{
        String sql = "SELECT count(G.id) "
                + " FROM game G\n"
                + " INNER JOIN download_history DH on DH.game_id = G.id"
                + " WHERE DH.account_id = ?";
        List params = new ArrayList();
        params.add(accountId);
        return executeQueryCountGame(sql, params);
    }
    
    public List<Game> findByName(String name, String os) throws Exception{
        String sql = "SELECT G.*, C.name category_name, C.code category_code "
                + " FROM game G\n"
                + " INNER JOIN category C on C.id = G.category_id"
                + " LEFT JOIN game_os GO ON GO.`game_id` = G.`id`\n"
                + " LEFT JOIN os O ON GO.`os_id` = O.`id`\n"
                + " WHERE upper(G.name) LIKE  concat('%',upper(?),'%')";
        List params = new ArrayList();
        params.add(name);
        if(os != null && !"".equals(os)){
            sql += " AND O.code = ?";
            params.add(os);
        }
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games;
        
     }
    
    /**
     * Danh sách game mới nhất, order theo date_create
     * @param catId chuyên mục id
     * @param page số trang
     * @param pageSize page size
     * @return 
     */
    public List<Game> findGameNewest(int catId, int page, int pageSize) throws Exception{
        String sql = "SELECT distinct G.*,C.name category_name,C.code category_code\n"
                + " FROM game G\n"
                + "LEFT JOIN category C ON G.`category_id` = C.`id`\n"
                + " WHERE G.status = "+ Game.ACTIVE;
        List params = new ArrayList();       
        if(catId > 0){
            sql += " AND C.id = ?";
            params.add(catId);              
        }
                sql += " ORDER BY date_create DESC";
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public int countGame() throws Exception{
        String sql = "SELECT count(G.id) count_game\n"
                + " FROM game G\n"
                +" WHERE G.status = "+Game.ACTIVE;
        List params = new ArrayList();
        return  executeQueryCountGame(sql, params);            
       
    }
    
    public List<Game> findGameMostView(int catId, int page, int pageSize) throws Exception{
        String sql = "SELECT distinct  G.*,C.name category_name,C.code category_code\n"
                + " FROM game G\n"
                + "LEFT JOIN category C ON G.`category_id` = C.`id`\n" 
                + " WHERE G.status = "+ Game.ACTIVE;;
        List params = new ArrayList();
         if(catId > 0){
            sql += " AND C.id = ?";
            params.add(catId);              
        }
        sql += " ORDER BY G.`view_count` DESC ";
        
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public List<Game> findGameMostDownload(int catId, int page, int pageSize) throws Exception{
        String sql = "SELECT distinct  G.*,C.name category_name,C.code category_code\n"
                + " FROM game G\n"
                + "LEFT JOIN category C ON G.`category_id` = C.`id`\n" 
                + " WHERE G.status = "+ Game.ACTIVE;;
        List params = new ArrayList();
         if(catId > 0){
            sql += " AND C.id = ?";
            params.add(catId);              
        }
        sql += " ORDER BY G.`download_count` DESC ";
        
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public List<Game> findByCategory(int catId, String os, int page, int pageSize) throws Exception{
        String sql = "SELECT distinct G.*,C.name category_name,C.code category_code\n" +
                    "FROM game G\n" +
                    "LEFT JOIN category C ON G.`category_id` = C.`id`\n" +
                    "LEFT JOIN game_os GO ON GO.`game_id` = G.`id`\n" +
                    "LEFT JOIN os O ON GO.`os_id` = O.`id`\n" +
                    "WHERE C.`id` = ? ";
        List params = new ArrayList();
        params.add(catId);
        if(os != null && !"".equals(os)){
            sql += " AND O.code = ?";
            params.add(os);
        }
        return loadGameWithPaging(sql, params, page, pageSize);
    }
    
    public int countGameByCategory(int catId, String os) throws Exception{
        String sql = "SELECT count(G.id)\n" +
                    "FROM game G\n" +
                    "LEFT JOIN category C ON G.`category_id` = C.`id`\n" +
                    "LEFT JOIN game_os GO ON GO.`game_id` = G.`id`\n" +
                    "LEFT JOIN os O ON GO.`os_id` = O.`id`\n" +
                    "WHERE C.`id` = ? ";
        List params = new ArrayList();
        params.add(catId);
        if(os != null && !"".equals(os)){
            sql += " AND O.code = ?";
            params.add(os);
        }
        return  executeQueryCountGame(sql, params);         
        
    }
    
    public List<Game> loadGameWithPaging(String sql, List params, int page, int pageSize )  throws Exception{
        int offset = (page -1 ) * pageSize;
        sql  += "\n LIMIT ?,?";
        params.add(offset);
        params.add(pageSize);
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games;
       
    }
    
    public Integer executeQueryCountGame(String sql, List<Object> parameters) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            if (parameters != null) {
                setParameters(stmt, parameters);
            }
            rs = stmt.executeQuery();
            rs.setFetchSize(100);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } finally {
            release(connection, stmt, rs);
        }
        return null;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Category;
import inet.entities.Game;
import inet.util.Constants;
import inet.util.StringUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TUTL
 */
public class GameDAO extends AbstractDAO {

    private RowMapper<Game> rowMapper;

    public GameDAO() throws Exception {

        super(Constants.DATABASE);

        rowMapper = new RowMapper<Game>() {

            GameOSDao dao = new GameOSDao();
            Game game;

            @Override
            public Game map(ResultSet rs) throws Exception {

                game = new Game();

                game.setId(rs.getString("id"));
                game.setCategoryId(rs.getString("category_id"));
                game.setName(rs.getString("name"));
                game.setAuthor(rs.getString("author"));
                game.setDescription(rs.getString("description"));
                game.setStatus(rs.getString("status"));
                game.setDateCreate(rs.getTimestamp("date_create"));
                game.setAvatar(rs.getString("avatar"));
                game.setSeoTitle(rs.getString("seo_title"));
                game.setSeoKeyword(rs.getString("seo_keyword"));
                game.setSeoDescription(rs.getString("seo_description"));
                game.setViewCount(rs.getInt("view_count"));
                game.setDownloadCount(rs.getInt("download_count"));
                game.setHot(rs.getInt("hot"));

                game.loadGameOS();

                return game;
            }
        };
    }

    public List<Game> findHot(int limit, int offset) throws Exception {

        String sql = "select * from game where hot = 1 and status = 1 limit ? offset ? ";

        List params = new ArrayList();
        params.add(limit);
        params.add(offset);

        return find(sql, params, rowMapper);

    }

    public int countHot() throws Exception {

        String sql = "select count(*) from game where hot >= 1 and status = 1";
        List params = new ArrayList();

        RowMapper<Integer> mapper = new RowMapper<Integer>() {
            @Override
            public Integer map(ResultSet resultSet) throws Exception {
                return resultSet.getInt(1);
            }
        };

        List<Integer> results = find(sql, params, mapper);
        return results.get(0);
    }

    public List<Game> findNewest(int limit, int offset) throws Exception {

        String sql = "select * from game order by id desc limit ? offset ? ";

        List params = new ArrayList();
        params.add(limit);
        params.add(offset);

        return find(sql, params, rowMapper);

    }

    public int countNewest() throws Exception {

        String sql = "select count(*) from game";
        List params = new ArrayList();

        RowMapper<Integer> mapper = new RowMapper<Integer>() {
            @Override
            public Integer map(ResultSet resultSet) throws Exception {
                return resultSet.getInt(1);
            }
        };

        List<Integer> results = find(sql, params, mapper);
        return results.get(0);
    }

    public List<Game> findViewest(int limit, int offset) throws Exception {

        String sql = "select * from game order by view_count desc limit ? offset ? ";

        List params = new ArrayList();
        params.add(limit);
        params.add(offset);

        return find(sql, params, rowMapper);

    }

    public int countViewest() throws Exception {

        String sql = " select count(*) from game ";
        List params = new ArrayList();

        RowMapper<Integer> mapper = new RowMapper<Integer>() {
            @Override
            public Integer map(ResultSet resultSet) throws Exception {
                return resultSet.getInt(1);
            }
        };

        List<Integer> results = find(sql, params, mapper);
        return results.get(0);
    }

    //hainp added 20160423
    public void increaseViewCount(String id) throws Exception{
        String sql = "UPDATE \n" +
                    "  game \n" +
                    "SET\n" +
                    "  view_count = view_count + 1 \n" +
                    "WHERE id = ? ";
        List params = new ArrayList();
        params.add(id);
        execute(sql, params);
    }
    
    public void increaseDownloadCount(String id) throws Exception{
        String sql = "UPDATE \n" +
                    "  game \n" +
                    "SET\n" +
                    "  download_count = download_count + 1 \n" +
                    "WHERE id = ? ";
        List params = new ArrayList();
        params.add(id);
        execute(sql, params);
    }
    
    public List<Game> findDownloadHistory(int accountId,int page, int pageSize) throws Exception{
        String sql = "SELECT G.*"
                + " FROM game G\n"
                + " INNER JOIN download_history DH on DH.game_id = G.id"
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
    //end
    
    public List<Game> findByCategory(int catId, String osId, int offset, int limit) throws Exception {

        if (catId == Category.HOT) {

            return findHot(limit, offset);

        } else if (catId == Category.NEW) {

            return findNewest(limit, offset);

        } else if (catId == Category.VIEW) {

            return findViewest(limit, offset);

        } else if (osId == null) {

            return findByCategory(catId, offset, limit);

        } else {

            String sql = "  SELECT DISTINCT   "
                    + "             c.*   "
                    + "     FROM   "
                    + "             os a,   "
                    + "             game_os b,   "
                    + "             game c   "
                    + "     WHERE   "
                    + "         1 = 1 AND a.id = b.os_id   "
                    + "         AND b.game_id = c.id   "
                    + "         AND a.id = ?   "
                    + "         AND c.category_id = ?   "
                    + "     ORDER BY date_create desc   "
                    + "     LIMIT ? OFFSET ?";

            List params = new ArrayList();
            params.add(osId);
            params.add(catId);
            params.add(offset);
            params.add(limit);
            return find(sql, params, rowMapper);

        }
    }

    private List<Game> findByCategory(int catId, int offset, int limit) throws Exception {

        String sql = "select * from game where category_id = ? and status = 1 order by date_create desc limit ? offset ? ";

        List params = new ArrayList();

        params.add(catId);
        params.add(limit);
        params.add(offset);
        return find(sql, params, rowMapper);

    }

    public List<Game> findByNameAndCategory(String name, String categoryId, int offset, int limit) throws Exception {

        String sql = "  SELECT  "
                + "         *  "
                + "     FROM "
                + "         game  "
                + "     WHERE 1 = 1  "
                + "         AND name LIKE CONCAT(?, '%')";

        List params = new ArrayList();
        params.add(name);

        if (!StringUtil.nvl(categoryId, "").equals("")) {
            sql += " AND category_id = ?";
            params.add(categoryId);
        }

        sql += " ORDER BY name LIMIT ? OFFSET ? ";
        params.add(limit);
        params.add(offset);

        return find(sql, params, rowMapper);

    }

    /**
     * Find all game
     *
     * @param status
     * @return
     */
    public List<Game> find(int status) throws Exception {
        String sql = "Select * from Game ";
        List params = new ArrayList();
        if (status != Game.ALL) {
            sql += " where status = ?";
            params.add(status);
        }
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games;
    }

    public Game findById(String id) throws Exception {
        String sql = "select * from game where id = ? ";
        List params = new ArrayList();
        params.add(id);
        List<Game> games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games.get(0);
    }

    /**
     * find game HOT theo category
     *
     * @param limit
     * @param offset
     * @return
     * @throws java.lang.Exception
     */
    public List<Game> findByName(String name, String os) throws Exception {
        String sql = "SELECT G.*, C.name category_name, C.code category_code "
                + " FROM game G   "
                + " INNER JOIN category C on C.id = G.category_id"
                + " LEFT JOIN game_os GO ON GO.`game_id` = G.`id`   "
                + " LEFT JOIN os O ON GO.`os_id` = O.`id`   "
                + " WHERE upper(G.name) LIKE  concat('%',upper(?),'%')";
        List params = new ArrayList();
        params.add(name);
        if (os != null && !"".equals(os)) {
            sql += " AND O.code = ?";
            params.add(os);
        }
        List<Game> games;
        games = find(sql, params, rowMapper);
        return games.isEmpty() ? null : games;

    }

    /**
     * Danh sách game mới nhất, order theo date_create
     *
     * @param catId chuyên mục id
     * @param page số trang
     * @param pageSize page size
     * @return
     */
    public List<Game> findGameNewest(int catId, int page, int pageSize) throws Exception {
        String sql = "SELECT distinct G.*,C.name category_name,C.code category_code   "
                + " FROM game G   "
                + "LEFT JOIN category C ON G.`category_id` = C.`id`   "
                + " WHERE G.status = " + Game.ACTIVE;
        List params = new ArrayList();
        if (catId > 0) {
            sql += " AND C.id = ?";
            params.add(catId);
        }
        sql += " ORDER BY date_create DESC";
        return loadGameWithPaging(sql, params, page, pageSize);
    }

    public int countGame() throws Exception {
        String sql = "SELECT count(G.id) count_game   "
                + " FROM game G   "
                + " WHERE G.status = " + Game.ACTIVE;
        List params = new ArrayList();
        return executeQueryCountGame(sql, params);

    }

    public List<Game> findGameMostView(int catId, int page, int pageSize) throws Exception {
        String sql = "SELECT distinct  G.*,C.name category_name,C.code category_code   "
                + " FROM game G   "
                + "LEFT JOIN category C ON G.`category_id` = C.`id`   "
                + " WHERE G.status = " + Game.ACTIVE;;
        List params = new ArrayList();
        if (catId > 0) {
            sql += " AND C.id = ?";
            params.add(catId);
        }
        sql += " ORDER BY G.`view_count` DESC ";

        return loadGameWithPaging(sql, params, page, pageSize);
    }

    public List<Game> findGameMostDownload(int catId, int page, int pageSize) throws Exception {
        String sql = "SELECT distinct  G.*,C.name category_name,C.code category_code   "
                + " FROM game G   "
                + "LEFT JOIN category C ON G.`category_id` = C.`id`   "
                + " WHERE G.status = " + Game.ACTIVE;;
        List params = new ArrayList();
        if (catId > 0) {
            sql += " AND C.id = ?";
            params.add(catId);
        }
        sql += " ORDER BY G.`download_count` DESC ";

        return loadGameWithPaging(sql, params, page, pageSize);
    }

    public int countGameByCategory(int catId, String os) throws Exception {

        if (catId == Category.HOT) {
            return countHot();
        } else if (catId == Category.VIEW) {
            return countViewest();
        } else if (catId == Category.NEW) {
            return countNewest();
        } else {
            String sql = "select count(*) from game where category_id = ? ";
            List params = new ArrayList();
            params.add(catId);
            if (!StringUtil.nvl(os, "").equals("")) {
                sql += " and os = ? ";
                params.add(os);
            }
            RowMapper<Integer> mapper = new RowMapper<Integer>() {
                @Override
                public Integer map(ResultSet resultSet) throws Exception {
                    return resultSet.getInt(1);
                }
            };

            List<Integer> results = find(sql, params, mapper);
            return results.get(0);
        }

    }

    public List<Game> loadGameWithPaging(String sql, List params, int page, int pageSize) throws Exception {
        int offset = (page - 1) * pageSize;
        sql += "    LIMIT ?,?";
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

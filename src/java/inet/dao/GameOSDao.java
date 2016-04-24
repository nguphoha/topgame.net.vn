/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.GameOS;
import inet.util.Constants;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class GameOSDao extends AbstractDAO {

    RowMapper<GameOS> rowMapper;

    public GameOSDao() throws Exception {

        super(Constants.DATABASE);

        rowMapper = new RowMapper<GameOS>() {

            GameOS gameOS;

            @Override
            public GameOS map(ResultSet rs) throws Exception {

                gameOS = new GameOS();
                gameOS.setId(rs.getString("id"));
                gameOS.setOsId(rs.getString("os_id"));
                gameOS.setGameId(rs.getString("game_id"));
                gameOS.setUrl(rs.getString("url"));
                gameOS.setStatus(rs.getString("status"));
                gameOS.setDateCreate(rs.getTimestamp("date_create"));
                return gameOS;
            }
        };

    }

    public Map<String, GameOS> getByGameID(String gameId) throws Exception {

        String sql = "select * from game_os where game_id = ? and status = 1";
        List prams = new ArrayList();
        prams.add(gameId);

        List<GameOS> results = find(sql, prams, rowMapper);
        Map<String, GameOS> gameOsese = new HashMap<String, GameOS>();

        for (GameOS item : results) {
            gameOsese.put(item.getOsId(), item);
        }

        return gameOsese;
    }

}

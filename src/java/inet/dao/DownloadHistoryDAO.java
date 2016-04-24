/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.constant.Constant;
import inet.entities.DownloadHistory;
import inet.entities.Game;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DownloadHistoryDAO extends AbstractDAO{
    private static DownloadHistoryDAO _instance = null;
    
    public static DownloadHistoryDAO getInstance()  throws Exception{
            if(_instance == null)
                _instance = new DownloadHistoryDAO();
            return _instance;
        
    }
    
    private RowMapper<DownloadHistory> rowMapper;
    
    private DownloadHistoryDAO() throws Exception{
        super(Constant.POOL_NAME_DEFAULT);
        
        rowMapper = new RowMapper<DownloadHistory>(){
            @Override
            public DownloadHistory map(ResultSet rs) throws Exception {
                DownloadHistory dlh = new DownloadHistory();
                dlh.setAccountId(rs.getString("account_id"));
                dlh.setGameId(rs.getString("game_id"));
                dlh.setDateCreate(rs.getTimestamp("date_create"));
                
                return dlh;
            }
            
        };
    }
    
//    public List<DownloadHistory> findHistory(int accId){
//        String sql = "SELECT DH.*\"
//                +   " FROM download_history DH";
//    }
}

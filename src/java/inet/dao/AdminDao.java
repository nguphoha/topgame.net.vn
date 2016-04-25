/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Account;
import inet.entities.Admin;
import inet.util.Constants;
import inet.util.StringUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AdminDao  extends AbstractDAO {
    RowMapper<Admin> rowMapper;
    
    public AdminDao()  throws Exception {
        super(Constants.DATABASE);
        rowMapper = new RowMapper<Admin>(){
            ModuleDao moduleDao = new ModuleDao();
            Admin admin = new Admin();
            @Override
            public Admin map(ResultSet resultSet) throws Exception {
                admin.setId(resultSet.getString("id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                admin.setMobile(resultSet.getString("mobile"));
                admin.setEmail(resultSet.getString("email"));
                admin.setStatus(resultSet.getString("status"));
                admin.setDateCreate(resultSet.getTimestamp("date_create"));
                admin.setModules(moduleDao.getByAdminId(admin.getId()));
                return admin;
            }
            
        };
    }
    
    public List<Admin> getDatas(String username, String status) throws Exception {
        String sql = "  SELECT "
                + "             a.*"
                + "     FROM"
                + "             admin a "
                + "     WHERE"
                + "             1 = 1 ";

        List params = new ArrayList();

        if (!StringUtil.nvl(username, "").equals("")) {
            sql += "             AND upper(a.username) LIKE CONCAT(upper(?),'%') ";
            params.add(username);
        }

        if (!StringUtil.nvl(status, "").equals("")) {
            sql += "             AND a.status = ? ";
            params.add(status);
        }
        
        sql += " ORDER BY ID DESC ";

        return find(sql, params, rowMapper);
    }

    public List<Admin> getByModule(String moduleId, String username, String status) throws Exception {

        String sql = "  SELECT "
                + "             a.*"
                + "     FROM"
                + "             admin a,"
                + "             admin_module b"
                + "     WHERE"
                + "             1 = 1 AND a.id = b.admin_id"
                + "             AND b.module_id = ?";

        List params = new ArrayList();
        params.add(moduleId);

        if (!StringUtil.nvl(username, "").equals("")) {
            sql += "             AND upper(a.username) LIKE CONCAT(upper(?),'%') ";
            params.add(username);
        }

        if (!StringUtil.nvl(status, "").equals("")) {
            sql += "             AND a.status = ? ";
            params.add(status);
        }

        return find(sql, params, rowMapper);
    }

    public Admin login(String username, String password) throws Exception {
        
        String sql = "  SELECT * "
                + "     FROM    admin "
                + "     WHERE   username = ? "
                + "             AND password = ?";
        
        List params = new ArrayList();
        params.add(username);
        params.add(password);

        List<Admin> results = find(sql, params, rowMapper);
        return results.isEmpty() ? null : results.get(0);
    }
}

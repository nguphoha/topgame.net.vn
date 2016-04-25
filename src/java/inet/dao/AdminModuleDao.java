/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.util.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AdminModuleDao extends AbstractDAO {

    public AdminModuleDao() throws Exception {
        super(Constants.DATABASE);
    }
    
    
    
    public void delete(String moduleId) throws Exception {
        
        String sql = "DELETE FROM admin_module WHERE module_id = ? ";
        
        List param = new ArrayList();
        param.add(moduleId);
        execute(sql, param);
        
    }
    
    public void insert(String adminId, String moduleId) throws Exception {
        
        String sql = "INSERT INTO admin_module(admin_id,module_id) VALUES(?,?)";
        
        List param = new ArrayList();        
        param.add(adminId);
        param.add(moduleId);
        execute(sql, param);        
    }
}

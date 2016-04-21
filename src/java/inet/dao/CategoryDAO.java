/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.constant.Constant;
import inet.entities.Category;
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
public class CategoryDAO extends AbstractDAO {

    
    
    private static CategoryDAO _instance = null;
    
    public static CategoryDAO getInstance(){
        try {
            if(_instance == null)
                _instance = new CategoryDAO();
            return _instance;
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private RowMapper<Category> rowMapper;

    private CategoryDAO() throws Exception {
        super(Constant.POOL_NAME_DEFAULT);

        rowMapper = new RowMapper<Category>() {
            Category category;

            @Override
            public Category map(ResultSet rs) throws SQLException {
                category = new Category();
                
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setCode(rs.getString("code"));
                category.setStatus(rs.getString("status"));
                category.setDateCreate(rs.getTimestamp("date_create"));
                category.setAvatar(rs.getString("avatar"));
                category.setSeoTitle(rs.getString("seo_title"));
                category.setSeoKeyword(rs.getString("seo_keyword"));
                category.setSeoDescription(rs.getString("seo_description"));
                return category;
            }
        };
    }

    public Category getByCode(String code) throws Exception {
        String sql = "select * from category where code = ?";
        List params = new ArrayList();
        params.add(code);
        List<Category> seos = find(sql, params, rowMapper);
        return seos.isEmpty() ? null : seos.get(0);

    }
    
    public Category getById(int id){
        String sql = "select * from category where id = ?";
        List params = new ArrayList();
        params.add(id);
        List<Category> seos;
        try {
            seos = find(sql, params, rowMapper);
            return seos.isEmpty() ? null : seos.get(0);
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Category();
        }
    }
    
    public List<Category> find(int status){
        String sql = "Select * from category ";
        List params = new ArrayList();
        if(status != Category.ALL){
            sql += " where status = ?";
            params.add(status);
        }
        List<Category> categories;
        try {
            categories = find(sql, params, rowMapper);
            return categories.isEmpty() ? null : categories;
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

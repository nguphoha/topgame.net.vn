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

    private RowMapper<Category> rowMapper;

    public CategoryDAO() throws Exception {
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

        String sql = "select * from category where code = ? ";
        
        List params = new ArrayList();
        params.add(code);
        
        List<Category> seos = find(sql, params, rowMapper);
        return seos.isEmpty() ? null : seos.get(0);

    }

    public Category getById(int id) throws Exception {

        String sql = "select * from category where id = ?";

        List params = new ArrayList();
        params.add(id);

        List<Category> categorys = find(sql, params, rowMapper);
        return categorys.isEmpty() ? null : categorys.get(0);

    }

    public List<Category> find() throws Exception {

        String sql = "  SELECT  "
                + "             a.*, COUNT(*) as total"
                + "     FROM "
                + "         category a "
                + "             LEFT JOIN "
                + "         game b ON a.id = b.category_id "
                + "     WHERE "
                + "             b.status IS NULL OR b.status = 1 "
                + "     GROUP BY id , name , code , status , date_create , avatar , seo_title , seo_keyword , seo_description"
                + "     ORDER BY a.name";

        RowMapper<Category> mapper = new RowMapper<Category>() {

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
                category.setTotalGame(rs.getInt("total"));
                return category;

            }
        };

        return find(sql, null, mapper);

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Seo;
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
public class SeoDao extends AbstractDAO {

    private RowMapper<Seo> rowMapper;

    public SeoDao() throws Exception {
        super("topgame");

        rowMapper = new RowMapper<Seo>() {
            Seo seo;

            @Override
            public Seo map(ResultSet rs) throws SQLException {
                seo = new Seo();
                seo.setDescription(rs.getString("description"));
                seo.setKeyword(rs.getString("keyword"));
                seo.setTitle(rs.getString("title"));
                seo.setPageDescription(rs.getString("page_description"));
                seo.setImage(rs.getString("image"));
                return seo;
            }
        };
    }

    public Seo getByURL(String url) {
        String sql = "select * from seo where url = ?";
        List params = new ArrayList();
        params.add(url);
        List<Seo> seos;
        try {
            seos = find(sql, params, rowMapper);
            return seos.isEmpty() ? null : seos.get(0);
        } catch (Exception ex) {
            Logger.getLogger(SeoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

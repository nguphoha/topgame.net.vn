/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Service;
import inet.util.Constants;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ServiceDao extends AbstractDAO {

    RowMapper<Service> rowMapper;

    public ServiceDao() throws Exception {

        super(Constants.DATABASE);

        rowMapper = new RowMapper<Service>() {

            Service service;

            @Override
            public Service map(ResultSet rs) throws Exception {

                service = new Service();
                service.setId(rs.getString("id"));
                service.setName(rs.getString("name"));
                service.setCode(rs.getString("code"));
                service.setPrice(rs.getInt("price"));
                service.setChargeCycle(rs.getInt("charge_cycle"));
                service.setDateCreate(rs.getTimestamp("date_create"));
                return service;

            }
        };
    }

    public Service getByCode(String code) throws Exception {

        String sql = "select * from service where code = ? and status = 1";
        List param = new ArrayList();
        param.add(code);

        List<Service> result = find(sql, param, rowMapper);
        return result.isEmpty() ? null : result.get(0);

    }

}

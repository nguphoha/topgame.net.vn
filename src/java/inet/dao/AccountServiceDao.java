/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.AccountService;
import inet.util.Constants;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TOM
 */
public class AccountServiceDao extends AbstractDAO {

    RowMapper<AccountService> rowMapper;

    public AccountServiceDao() throws Exception {

        super(Constants.DATABASE);

        rowMapper = new RowMapper<AccountService>() {

            AccountService service;

            @Override
            public AccountService map(ResultSet rs) throws Exception {

                service = new AccountService();
                service.setAccountId(rs.getString("account_id"));
                service.setId(rs.getString("id"));
                service.setServiceId(rs.getString("service_id"));
                service.setServiceCode(rs.getString("service_code"));
                service.setMobile(rs.getString("mobile"));
                service.setDateCreate(rs.getTimestamp("date_create"));
                service.setDateLatestCreate(rs.getTimestamp("date_latest_create"));
                service.setDateCancel(rs.getTimestamp("date_cancel"));
                service.setDateRenew(rs.getTimestamp("date_renew"));
                service.setDateExpire(rs.getTimestamp("date_expire"));
                service.setPartner(rs.getString("partner"));
                service.setUrlRegister(rs.getString("url_register"));
                service.setStatus(rs.getString("status"));
                service.setProperties(rs.getString("properties"));
                service.setChannel(rs.getString("channel"));
                service.setChargeSequence(rs.getInt("charge_sequence"));

                return service;

            }
        };
    }

    public List<AccountService> getByMobile(String mobile) throws Exception {

        String sql = "select * from account_service where mobile = ?";
        List params = new ArrayList();
        params.add(mobile);
        return find(sql, params, rowMapper);

    }

}

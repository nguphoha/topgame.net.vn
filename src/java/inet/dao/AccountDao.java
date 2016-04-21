/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.dao;

import inet.common.database.dao.AbstractDAO;
import inet.common.database.dao.RowMapper;
import inet.entities.Account;
import inet.entities.AccountService;
import inet.util.Constants;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TOM
 */
public class AccountDao extends AbstractDAO {

    RowMapper<Account> mapper;

    public AccountDao() throws Exception {

        super(Constants.DATABASE);

        mapper = new RowMapper<Account>() {

            AccountServiceDao accountServiceDao = new AccountServiceDao();
            Account account;

            @Override
            public Account map(ResultSet rs) throws Exception {

                account = new Account();
                account.setId(rs.getString("id"));
                account.setMobile(rs.getString("mobile"));
                account.setPassword(rs.getString("password"));
                account.setStatus(rs.getString("status"));
                account.setDateCreate(rs.getTimestamp("date_create"));
                account.setKey(rs.getString("aes_key"));

                List<AccountService> services = accountServiceDao.getByMobile(account.getMobile());
                for (AccountService item : services) {
                    account.putService(item.getServiceCode(), item);
                }

                return account;
            }
        };
    }

    public Account getByMobile(String mobile) throws Exception {

        String sql = "select * from account where mobile = ?";

        List params = new ArrayList();
        params.add(mobile);

        List<Account> results = find(sql, params, mapper);
        return results.isEmpty() ? null : results.get(0);

    }
    public Account login(String mobile,String password) throws Exception {

        String sql = "select * from account where mobile = ? and upper(password) = upper(?)";

        List params = new ArrayList();
        params.add(mobile);
        params.add(password);

        List<Account> results = find(sql, params, mapper);
        return results.isEmpty() ? null : results.get(0);

    }

}

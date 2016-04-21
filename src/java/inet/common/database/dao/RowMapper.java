package inet.common.database.dao;

import java.sql.ResultSet;

public interface RowMapper<T> {
	
	T map(ResultSet resultSet) throws Exception;
	
}

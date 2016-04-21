package inet.common.database.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import inet.common.database.annotation.Column;
import inet.common.database.annotation.Table;
import inet.common.database.pool.DBPool;

public abstract class AbstractDAO {

    protected DBPool poolX;

    public AbstractDAO() {
    }

    public AbstractDAO(String poolName) throws Exception {
        poolX = DBPool.getInstance(poolName);
    }

    protected Connection getConnection() {
        return poolX.getConnection();
    }

    protected void release(Connection conn, PreparedStatement stmt, ResultSet rs) {
        poolX.releaseConnection(conn, stmt, rs);
    }

    protected void release(Connection conn, PreparedStatement stmt) {
        poolX.releaseConnection(conn, stmt);
    }

    protected DBPool getDatabasePool(String poolName) throws Exception {
        return DBPool.getInstance(poolName);
    }

    public String getSequeceValue(Connection connection, String sequence)
            throws Exception {
        boolean initConnection = false;
        try {
            if (connection == null) {
                connection = getConnection();
                initConnection = true;
            }
            return DBPool.getSequenceValue(connection, sequence, true);
        } finally {
            if (initConnection) {
                release(connection, null);
            }
        }
    }

    public long getSequece(String sequence) throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            return Long.parseLong(DBPool.getSequenceValue(connection,
                    sequence, false));
        } finally {
            release(connection, null);
        }
    }

    public String getSequenceValue(String tableName, String databaseName) throws Exception {
        // SQL command to sequence value
        String strSQL = "SELECT auto_increment FROM information_schema.TABLES WHERE TABLE_NAME = '" + tableName + "' and TABLE_SCHEMA = '" + databaseName + "'";

        // Get query data
        ResultSet rs = null;
        Statement stmt = null;
        Connection cn = null;
        try {
            cn = poolX.getConnection();
            stmt = cn.createStatement();
            rs = stmt.executeQuery(strSQL);
            rs.next();
            String strReturn = rs.getString(1);
            rs.close();
            stmt.close();
            return strReturn;
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            release(cn, null);
        }
    }

    protected void setParameters(PreparedStatement statement,
            List<Object> parameters) throws SQLException {

        for (int i = 0, length = parameters.size(); i < length; i++) {
            Object parameter = parameters.get(i);
            if (null == parameter) {
                statement.setObject(i + 1, null);
            } else if (parameter instanceof String) {
                statement.setString(i + 1, (String) parameter);
            } else if (parameter instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) parameter);
            } else if (parameter instanceof Character) {
                statement.setString(i + 1, String.valueOf(parameter));
            } else if (parameter instanceof Byte) {
                statement.setByte(i + 1, (Byte) parameter);
            } else if (parameter instanceof Short) {
                statement.setShort(i + 1, (Short) parameter);
            } else if (parameter instanceof Integer) {
                statement.setInt(i + 1, (Integer) parameter);
            } else if (parameter instanceof Long) {
                statement.setLong(i + 1, (Long) parameter);
            } else if (parameter instanceof Float) {
                statement.setFloat(i + 1, (Float) parameter);
            } else if (parameter instanceof Double) {
                statement.setDouble(i + 1, (Double) parameter);
            } else if (parameter instanceof Timestamp) {
                statement.setTimestamp(i + 1, (Timestamp) parameter);
            } else {
                throw new IllegalArgumentException(
                        String.format("GenericDao Exception: Unknown type of the parameter is found. [param: %s, paramIndex: %s]", parameter, i + 1));
            }
        }
    }

    public <T> List<T> find(String sql, List<Object> parameters, RowMapper<T> rowMapper) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<T> datas = new ArrayList<T>();

        try {
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            if (parameters != null) {
                setParameters(stmt, parameters);
            }
            rs = stmt.executeQuery();
            rs.setFetchSize(100);
            while (rs.next()) {
                datas.add(rowMapper.map(rs));
            }
        } finally {
            release(connection, stmt, rs);
        }
        return datas;

    }

    private <T> String buildSQLInsertByEntity(T entity) throws Exception {
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception(
                    "GenericDao Exception: entity is not type of annotation Table!");
        }

        String tableName = table.name();
        if (tableName == null) {
            throw new Exception(
                    "GenericDao Exception: Element \"name\" of annotation Table is null or is not define!");
        }

        // build structure query language
        StringBuilder sqlValue = new StringBuilder();
        sqlValue.append(" VALUES(");

        StringBuilder sqlHead = new StringBuilder();
        sqlHead.append("INSERT INTO ").append(table.name()).append(" (");

        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        for (Field item : fields) {

            Column column = item.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            String name = column.name();
            if (name == null) {
                throw new Exception(
                        "GenericDao Exception: Element \"name\" of annotation Column is null or is not define!");
            }
            if (i == 0) {
                sqlHead.append(name);
                sqlValue.append("?");
            } else {
                sqlHead.append(",").append(name);
                sqlValue.append(",").append("?");
            }
            i++;
        }

        sqlHead.append(")");
        sqlValue.append(")");
        sqlHead.append(sqlValue);
        return sqlHead.toString();
    }

    private static <T> String buildSQLDeleteByEntity(T entity) throws Exception {

        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception(
                    "GenericDao Exception: entity is not type of annotation Table!");
        }

        String tableName = table.name();
        if (tableName == null) {
            throw new Exception(
                    "GenericDao Exception: Element \"name\" of annotation Table is null or is not define!");
        }

        // build structure query language
        StringBuilder sqlValue = new StringBuilder();

        StringBuilder sqlHead = new StringBuilder();
        sqlHead.append("DELETE FROM ").append(table.name()).append(" WHERE ");

        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        for (Field item : fields) {

            Column column = item.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            boolean isPrimaryKey = column.PK();
            if (!isPrimaryKey) {
                continue;
            }

            String name = column.name();
            if (name == null) {
                throw new Exception(
                        "GenericDao Exception: Element \"name\" of annotation Column is null or is not define!");
            }

            if (i == 0) {
                sqlValue.append(name).append(" = ? ");
            } else {
                sqlValue.append("AND ").append(name).append(" = ? ");
            }
            i++;
        }

        sqlHead.append(sqlValue);
        return sqlHead.toString();
    }

    private static <T> String buildSQLUpdateByEntity(T entity) throws Exception {

        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new Exception(
                    "GenericDao Exception: entity is not type of annotation Table!");
        }

        String tableName = table.name();
        if (tableName == null) {
            throw new Exception(
                    "GenericDao Exception: Element \"name\" of annotation Table is null or is not define!");
        }

        // build structure query language
        StringBuilder sqlValue = new StringBuilder();
        sqlValue.append(" WHERE ");

        StringBuilder sqlHead = new StringBuilder();
        sqlHead.append("UPDATE ").append(table.name()).append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field item : fields) {

            Column column = item.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            String name = column.name();
            if (name == null) {
                throw new Exception(
                        "GenericDao Exception: Element \"name\" of annotation Column is null or is not define!");
            }

            boolean isPrimaryKey = column.PK();
            if (!isPrimaryKey) {
                sqlHead.append(name).append(" = ?, ");
            } else {
                sqlValue.append(name).append(" = ? AND ");
            }
        }

        String head = sqlHead.toString();
        head = head.substring(0, head.lastIndexOf(","));

        String value = sqlValue.toString();
        value = value.substring(0, value.lastIndexOf("AND"));
        return head + " " + value;
    }

    public <T> int insert(T entity) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        Connection connection = null;
        PreparedStatement stmt = null;

        try {

            connection = getConnection();
            stmt = connection.prepareStatement(buildSQLInsertByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            String tableName = clazz.getAnnotation(Table.class).name();

            for (Field field : fields) {

                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();
                Object value = field.get(entity);
//				if (isPrimaryKey && value == null) {
//					String strPrimaryKey = getSequenceValue(tableName + "_SEQ",
//							false);
//					params.add(strPrimaryKey);
//				} else {
                params.add(value);
//				}
            }

            setParameters(stmt, params);
            return stmt.executeUpdate();
        } finally {
            release(connection, stmt);
        }
    }

    public <T> int insert(T entity, Connection connection) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement(buildSQLInsertByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            String tableName = clazz.getAnnotation(Table.class).name();

            for (Field field : fields) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();
                Object value = field.get(entity);
//				if (isPrimaryKey && value == null) {
//					String strPrimaryKey = getSequenceValue(tableName + "_SEQ",
//							false);
//					params.add(strPrimaryKey);
//				} else {
                params.add(value);
//				}
            }

            setParameters(stmt, params);
            return stmt.executeUpdate();
        } finally {
            release(null, stmt);
        }
    }

    public <T> int[] insert(List<T> entities) throws Exception {

        // validate object
        if (entities == null || entities.isEmpty()) {
            throw new Exception(
                    "GenericDao Exception: entities param is not null or empty!");
        }

        String sql = buildSQLInsertByEntity(entities.get(0));

        // process parameters
        Connection connection = null;
        PreparedStatement stmt = null;

        try {

            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            List<Object> params = null;

            for (T entity : entities) {

                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                String tableName = clazz.getAnnotation(Table.class).name();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

//					boolean isPrimaryKey = column.PK();
                    Object value = field.get(entity);
//					if (isPrimaryKey && value == null) {
//						String strPrimaryKey = getSequenceValue(tableName
//								+ "_SEQ", false);
//						params.add(strPrimaryKey);
//					} else {
                    params.add(value);
//					}
                }
                setParameters(stmt, params);
                stmt.addBatch();
            }
            return stmt.executeBatch();
        } finally {
            release(connection, stmt);
        }
    }

    public <T> int[] insert(List<T> entities, Connection connection)
            throws Exception {

        // validate object
        if (entities == null || entities.isEmpty()) {
            throw new Exception(
                    "GenericDao Exception: entities param is not null or empty!");
        }

        String sql = buildSQLInsertByEntity(entities.get(0));

        // process parameters
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement(sql);
            List<Object> params = null;

            for (T entity : entities) {

                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                String tableName = clazz.getAnnotation(Table.class).name();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    boolean isPrimaryKey = column.PK();
                    Object value = field.get(entity);
//					if (isPrimaryKey && value == null) {
//						String strPrimaryKey = getSequenceValue(tableName
//								+ "_SEQ", false);
//						params.add(strPrimaryKey);
//					} else {
                    params.add(value);
//					}
                }
                setParameters(stmt, params);
                stmt.addBatch();
            }
            return stmt.executeBatch();
        } finally {
            release(null, stmt);
        }
    }

    public <T> int delete(T entity) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        Connection connection = null;
        PreparedStatement stmt = null;

        try {

            connection = getConnection();
            stmt = connection.prepareStatement(buildSQLDeleteByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();

                if (!isPrimaryKey) {
                    continue;
                }

                Object value = field.get(entity);
                params.add(value);
            }
            if (!params.isEmpty()) {
                setParameters(stmt, params);
                return stmt.executeUpdate();
            } else {
                return 0;
            }
        } finally {
            release(connection, stmt);
        }
    }

    public <T> int delete(T entity, Connection connection) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement(buildSQLDeleteByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();

                if (!isPrimaryKey) {
                    continue;
                }

                Object value = field.get(entity);
                params.add(value);
            }
            if (!params.isEmpty()) {
                setParameters(stmt, params);
                return stmt.executeUpdate();
            } else {
                return 0;
            }
        } finally {
            release(null, stmt);
        }
    }

    public <T> int[] delete(List<T> entities) throws Exception {

        if (entities == null || entities.isEmpty()) {
            throw new Exception(
                    "GenericDao Exception: entities param is null or entities is empty!");
        }

        Connection connection = null;
        PreparedStatement stmt = null;
        List<Object> params = null;

        try {

            String sql = buildSQLDeleteByEntity(entities.get(0));
            connection = getConnection();
            stmt = connection.prepareStatement(sql);

            for (T entity : entities) {

                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    boolean isPrimaryKey = column.PK();
                    if (!isPrimaryKey) {
                        continue;
                    }
                    Object value = field.get(entity);
                    params.add(value);
                }

                setParameters(stmt, params);
                stmt.addBatch();

            }
            return stmt.executeBatch();
        } finally {
            release(connection, stmt);
        }
    }

    public <T> int[] delete(List<T> entities, Connection connection)
            throws Exception {

        if (entities == null || entities.isEmpty()) {
            throw new Exception(
                    "GenericDao Exception: entities param is null or entities is empty!");
        }

        PreparedStatement stmt = null;
        List<Object> params = null;

        try {

            String sql = buildSQLDeleteByEntity(entities.get(0));
            stmt = connection.prepareStatement(sql);

            for (T entity : entities) {

                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    boolean isPrimaryKey = column.PK();
                    if (!isPrimaryKey) {
                        continue;
                    }
                    Object value = field.get(entity);
                    params.add(value);
                }

                setParameters(stmt, params);
                stmt.addBatch();

            }
            return stmt.executeBatch();
        } finally {
            release(null, stmt);
        }
    }

    public <T> int update(T entity) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        Connection connection = null;
        PreparedStatement stmt = null;

        List<Object> pkValues = new ArrayList<Object>();

        try {

            connection = getConnection();
            stmt = connection.prepareStatement(buildSQLUpdateByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();
                Object value = field.get(entity);
                if (!isPrimaryKey) {
                    params.add(value);
                } else {
                    pkValues.add(value);
                }
            }

            if (pkValues.isEmpty()) {
                extracted();
            }

            if (!params.isEmpty()) {
                params.addAll(pkValues);
                setParameters(stmt, params);
                return stmt.executeUpdate();
            } else {
                return 0;
            }
        } finally {
            release(connection, stmt);
        }
    }

    public <T> int update(T entity, Connection connection) throws Exception {

        // validate object
        if (entity == null) {
            throw new Exception("GenericDao Exception: entities param is null");
        }

        // process parameters
        PreparedStatement stmt = null;

        List<Object> pkValues = new ArrayList<Object>();

        try {

            stmt = connection.prepareStatement(buildSQLUpdateByEntity(entity));
            List<Object> params = new ArrayList<Object>();

            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                boolean isPrimaryKey = column.PK();
                Object value = field.get(entity);
                if (!isPrimaryKey) {
                    params.add(value);
                } else {
                    pkValues.add(value);
                }
            }

            if (pkValues.isEmpty()) {
                extracted();
            }

            if (!params.isEmpty()) {
                params.addAll(pkValues);
                setParameters(stmt, params);
                return stmt.executeUpdate();
            } else {
                return 0;
            }
        } finally {
            release(null, stmt);
        }
    }

    public <T> int[] update(List<T> entities) throws Exception {

        // validate object
        if (entities == null || entities.isEmpty()) {
            throw new Exception(
                    "GenericDao Exception: entities param is null or entities is empty");
        }

        // process parameters
        String sql = buildSQLUpdateByEntity(entities.get(0));

        Connection connection = null;
        PreparedStatement stmt = null;

        List<Object> pkValues = null;
        List<Object> params = null;

        try {

            connection = getConnection();
            stmt = connection.prepareStatement(sql);

            for (T entity : entities) {

                pkValues = new ArrayList<Object>();
                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    boolean isPrimaryKey = column.PK();
                    Object value = field.get(entity);
                    if (!isPrimaryKey) {
                        params.add(value);
                    } else {
                        pkValues.add(value);
                    }
                }

                if (pkValues.isEmpty()) {
                    extracted();
                }

                params.addAll(pkValues);
                setParameters(stmt, params);
                stmt.addBatch();

            }

            return stmt.executeBatch();

        } finally {
            release(connection, stmt);
        }
    }

    private void extracted() throws Exception {
        throw new Exception("GenericDao Exception: Missing IN OUT");
    }

    public <T> int[] update(List<T> entities, Connection connection)
            throws Exception {

        // validate object
        if (entities == null) {
            throw new Exception(
                    "GenericDao Exception: entities param is null or entities is empty");
        }

        // process parameters
        String sql = buildSQLUpdateByEntity(entities.get(0));

        PreparedStatement stmt = null;

        List<Object> pkValues = null;
        List<Object> params = null;

        try {

            stmt = connection.prepareStatement(sql);

            for (T entity : entities) {

                pkValues = new ArrayList<Object>();
                params = new ArrayList<Object>();

                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    boolean isPrimaryKey = column.PK();
                    Object value = null;
                    if (!isPrimaryKey) {
                        value = field.get(entity);
                        params.add(value);
                    } else {
                        pkValues.add(value);
                    }
                }

                if (pkValues.isEmpty()) {
                    extracted();
                }

                params.addAll(pkValues);
                setParameters(stmt, params);
                stmt.addBatch();

            }

            return stmt.executeBatch();

        } finally {
            release(null, stmt);
        }
    }

    public Object execute(String sql, List<Object> parameters) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = getConnection();
            stmt = connection.prepareStatement(sql);
            setParameters(stmt, parameters);
            return stmt.executeUpdate();
        } finally {
            release(connection, stmt);
        }
    }

    public Object execute(String sql, List<Object> parameters,
            Connection connection) throws Exception {

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);
            setParameters(stmt, parameters);
            return stmt.executeUpdate();
        } finally {
            release(null, stmt);
        }
    }
    
    public String getString(ResultSet rs,String colName, String defaultValue){
        try{
            return rs.getString(colName);
        }catch(SQLException e){
            return defaultValue;
        }
    }
}

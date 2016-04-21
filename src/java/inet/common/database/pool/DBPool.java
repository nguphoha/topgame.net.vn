package inet.common.database.pool;

import inet.util.Encrypter;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;

public class DBPool {

    private static ConnectionPoolManager poolManager;
    private static ConcurrentHashMap<String, DBPool> INSTANCE = new ConcurrentHashMap<String, DBPool>();
    
    private String poolName;
    
    private DBPool() {}

    private DBPool(String poolName) {
        this.poolName = poolName;
    }
    
    public static void loadConfiguration(Properties configs) throws Exception {
    	
    	Enumeration e = configs.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.contains("password")) {
                String value = Encrypter.decrypt(configs.getProperty(key));
                configs.setProperty(key, value);
            }
        }
        
        poolManager = ConnectionPoolManager.getInstance(configs);
        ConnectionPool[] pools = poolManager.getPools().toArray(new ConnectionPool[poolManager.getPools().size()]);
        for (ConnectionPool pool : pools) {
            DBPool poolX = new DBPool(pool.getName());
            INSTANCE.put(pool.getName(), poolX);
        }
        
    }   
    
 public static void loadConfiguration(InputStream inputStream) throws Exception {
    	Properties properties = new Properties();
    	properties.load(inputStream);    	
        loadConfiguration(properties);
    }
    
    public static DBPool getInstance(String poolName) throws Exception {
        DBPool poolX = INSTANCE.get(poolName);
        if (poolX == null) {
            throw new Exception("Database Pool " + poolName + " not found!");
        } else {
            return poolX;
        }
    }

    // Remove and close all connections in pool
    public static void releaseAll() {
        poolManager.release();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = poolManager.getConnection(poolName, 3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection conn, PreparedStatement preStmt) {
        try {
            if (preStmt != null) {
                preStmt.close();
            }

            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.setAutoCommit(true);
                }
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void releaseConnection(Connection conn, PreparedStatement preStmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        releaseConnection(conn, preStmt);
    }

    public void releaseConnection(Connection conn, PreparedStatement preStmt, Statement stmt, ResultSet rs) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        releaseConnection(conn, preStmt, rs);
    }

    public static String getSequenceValue(Connection cn, String sequenceName, boolean bAutoCreate) throws Exception {
        // SQL command to sequence value
        String strSQL = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";

        // Get query data
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = cn.createStatement();
            rs = stmt.executeQuery(strSQL);
            rs.next();
            String strReturn = rs.getString(1);
            rs.close();
            stmt.close();
            return strReturn;
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().startsWith("ORA-02289")) {
                if (!bAutoCreate) {
                    throw new Exception("getSequenceValue");
                } else {
                    stmt = cn.createStatement();
                    stmt.executeUpdate("CREATE SEQUENCE " + sequenceName + " START WITH 2");
                    stmt.close();
                    return "1";
                }
            } else {
                throw e;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }
}

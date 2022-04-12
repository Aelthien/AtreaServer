package atrea.server.engine.networking.databases;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtreaConnectionPool
{
    private static String URL = "jdbc:mysql://localhost:3306/atrea";
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String USER = "root";
    private static String PASS = "";

    private static List<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();
    private static int POOL_SIZE = 50;

    private static ComboPooledDataSource cpds;

    public AtreaConnectionPool()
    {
    }

    static
    {
        try
        {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(DRIVER);
            cpds.setJdbcUrl(URL);
            cpds.setUser(USER);
            cpds.setPassword(PASS);

            cpds.setInitialPoolSize(POOL_SIZE);
            cpds.setMinPoolSize(POOL_SIZE);
            cpds.setMaxPoolSize(200);
            cpds.setAcquireIncrement(5);

            } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return cpds;
    }

    public static Connection getConnection()
    {
        try {
            Connection connection = cpds.getConnection();

            if (connection.isValid(20))
                return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String getUrl() {
        return URL;
    }

    public String getUser() {
        return USER;
    }

    public String getPass() {
        return PASS;
    }
}
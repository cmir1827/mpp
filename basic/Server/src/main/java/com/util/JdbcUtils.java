package com.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by sergiubulzan on 17/06/2017.
 */
public class JdbcUtils {
    private Properties jdbcProps;

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }


    private Connection instance=null;


    private Connection getNewConnection(){
        String user=jdbcProps.getProperty("user");
        String password=jdbcProps.getProperty("password");
        String serverName=jdbcProps.getProperty("serverName");
        String dataBase=jdbcProps.getProperty("dataBase");
        Connection con=null;

        try{
            Context context = new InitialContext();
        }catch (NamingException xx){
            System.out.println("Error initializing context");
        }

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(dataBase);

        Connection conn = null;
        try{
            conn = dataSource.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }


        return conn;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }

    static public Properties getProps() {
        // move props on a better place
        String resourceName = "db.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}

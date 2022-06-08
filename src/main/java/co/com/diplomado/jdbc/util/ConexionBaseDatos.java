package co.com.diplomado.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso";
    private static String user = "root";
    private static String password = "admin";
    private static Connection conn;

    public static Connection getInstance() throws SQLException{
        if (null == conn){
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}

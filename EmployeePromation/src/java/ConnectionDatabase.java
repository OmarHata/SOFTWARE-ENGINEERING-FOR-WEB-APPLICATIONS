/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    private ConnectionDatabase() {
    }

    public static Connection getConnections() throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/organizationDATABASE?useSSL=false";
            con = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
        }
        return con;
    }
}

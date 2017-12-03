/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id1212.homework3.adisu.rmi.server.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ema
 */
public class DBConnection {

    private static Connection con = null;
    private static Statement st = null;
    private static PreparedStatement pst = null;
    private static final String URL = "jdbc:mysql://localhost:3306/FileCatalog?useSSL=false";
    private static final String PASS = "root", USER = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static int nc;
    private static final int INSERTED = 0;
    private static final int USER_CONFLICT = 1;

    public static Connection connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public static PreparedStatement insert(String sql) {
        try {
            pst = connect().prepareStatement(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pst;
    }

    public static int uploadFilesToDB(String fname, String access, byte[] content, String owner, String permission) {
        int result;
        String query = "insert into FilesTable(Name, AccessType, Content, owner, PermissionType) values(?,?,?,?,?)";
        try {
            pst = insert(query);
            pst.setString(1, fname);
            pst.setString(2, access);
            pst.setBytes(3, content);
            pst.setString(4, owner);
            pst.setString(5, permission);
            pst.executeUpdate();
            result = INSERTED;
        } catch (SQLException ex) {
            result = USER_CONFLICT;
            System.err.println(ex);
        }

        return result;

    }

    public static int delete(String fileName) {
        String query = "delete from FilesTable where Name = " + "'" + fileName + "'";
        int rsa = 0;
        try {
            st = connect().createStatement();
            rsa = st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rsa;
    }
}

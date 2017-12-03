/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id1212.homework3.adisu.rmi.server.model;

import id1212.homework3.adisu.rmi.server.integration.DBConnection;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ema
 */
public class LoginClass implements Serializable {

    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private static final int INSERTED = 0;
    private static final int USER_CONFLICT = 1;
    

    public boolean checkLogin(String user, String pass){
        boolean bool = false;
        String query = "select * from account where userName=? and password=?";
        try {
            pst = DBConnection.insert(query);
            pst.setString(1, user);
            pst.setString(2, pass);
            rs = pst.executeQuery();
            if (rs.next()) {
                bool = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return bool;
    }

    public int createAccount(String name, String user, String email, String password){
       int result;
        String query = "insert into account(FullName, UserName,Email, Password) values(?,?,?,?)";
        try {
            pst = DBConnection.insert(query);
            pst.setString(1, name);
            pst.setString(2, user);
            pst.setString(3, email);
            pst.setString(4, password);
            pst.executeUpdate();
            result = INSERTED;
        } catch (SQLException ex) {
            result = USER_CONFLICT;
            System.err.println(ex);
        }

        return result;
    }
}

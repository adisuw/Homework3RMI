/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.server.model;

import id1212.homework3.adisu.rmi.server.integration.DBConnection;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ema
 */
public class FileHandlerClass implements Serializable {

    private static final int INSERTED = 0;
    private static final int USER_CONFLICT = 1;

    private String fileName;
    private String access;
    private String owner;
    private String permission;
    private byte[] content;
    private String size;


    public int uploadFile(String fname, String access, byte[] content, String owner, String permission) throws Exception {
        return DBConnection.uploadFilesToDB(fname, access, content, owner, permission);

    }

    public List<FileHandlerClass> viewAll(String user) throws Exception {
        String sql = "select * from FilesTable where owner = " + "'" + user + "'OR AccessType = 'Public'";

        List list = new ArrayList();
        ResultSet rs;
        Statement st;
        try {
            st = DBConnection.connect().createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {

                this.setFileName(rs.getString("Name"));
                this.setAccess(rs.getString("AccessType"));
                this.setContent(rs.getBytes("content"));
                this.setOwner(rs.getString("owner"));
                this.setPermission(rs.getString("PermissionType"));

                list.add(this.getFileName());
                list.add(this.getAccess());
                list.add(this.getOwner());
                list.add(this.getPermission());
                list.add(this.getSize());
            }
        } catch (SQLException ex) {
            System.err.println("Error... " + ex);
        }
        return list;
    }

    public byte[] download(String path) throws Exception {
        byte[] cont = null;
        String query = "select Content from FilesTable where Name = " + "'" + path + "'";
        ResultSet rs;
        Statement st;
        try {
            st = DBConnection.connect().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                cont = rs.getBytes("content");
            }
        } catch (SQLException ex) {
            System.err.println("Error... " + ex);
        }
        return cont;
    }

    public int deleteFile(String fileName) throws Exception {
        return DBConnection.delete(fileName);
    }

    public List<FileHandlerClass> searchByName(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFileName() {
        return fileName;
    }

    public String getAccess() {
        return access;
    }

    public String getOwner() {
        return owner;
    }

    public String getPermission() {
        return permission;
    }

    public byte[] getContent() {
        return content;
    }

    public String getSize() {
        return size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSize(int size) {
        this.size = String.valueOf(size);
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setContent(byte[] content) {
        setSize(content.length);
        this.content = content;
    }

}

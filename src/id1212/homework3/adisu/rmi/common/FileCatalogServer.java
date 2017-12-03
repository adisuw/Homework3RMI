/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.common;

import id1212.homework3.adisu.rmi.server.model.FileHandlerClass;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author ema
 */
public interface FileCatalogServer extends Remote {

    public static final String SERVER_NAME_IN_REGISTRY = "FILE_CATALOG_SERVER";

    public boolean checkLogin(String user, String pass) throws Exception;

    public int createAccount(String name, String user, String email, String password) throws Exception;

    public int uploadFile(String fname, String access, byte[] content, String owner, String permission) throws Exception;

    public List<FileHandlerClass> viewAll(String user) throws Exception;

    public byte[] download(String path) throws Exception;

    public int deleteFile(String fileName) throws Exception;

    public List<FileHandlerClass> searchByName(String name) throws Exception;

}

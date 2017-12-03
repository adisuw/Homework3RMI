/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.server.controller;

import id1212.homework3.adisu.rmi.common.FileCatalogServer;
import id1212.homework3.adisu.rmi.server.model.FileHandlerClass;
import id1212.homework3.adisu.rmi.server.model.LoginClass;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author ema
 */
public class FileCatalogServerController extends UnicastRemoteObject implements FileCatalogServer{
    private final LoginClass log = new LoginClass();
    private final FileHandlerClass handler = new FileHandlerClass();
    public FileCatalogServerController() throws Exception{
        super();
    }
    @Override
    public boolean checkLogin(String user, String pass) throws Exception {
        return log.checkLogin(user, pass);
    }

    @Override
    public int createAccount(String name, String user, String email, String password) throws Exception {
        return log.createAccount(name, user, email, password);
    }

    @Override
    public int uploadFile(String fname, String access, byte[] content, String owner, String permission) throws Exception {
        return handler.uploadFile(fname, access, content, owner, permission);
    }

    @Override
    public List<FileHandlerClass> viewAll(String user) throws Exception {
        return handler.viewAll(user);
    }

    @Override
    public byte[] download(String path) throws Exception {
        return handler.download(path);
    }
    @Override
    public int deleteFile(String fileName) throws Exception{
        return handler.deleteFile(fileName);
    }
    @Override
    public List<FileHandlerClass> searchByName(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

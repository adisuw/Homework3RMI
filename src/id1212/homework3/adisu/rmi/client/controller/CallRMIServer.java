/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id1212.homework3.adisu.rmi.client.controller;

import id1212.homework3.adisu.rmi.common.FileCatalogServer;
import id1212.homework3.adisu.rmi.common.Messanger;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ema
 */
public class CallRMIServer extends UnicastRemoteObject implements Messanger {

    public static FileCatalogServer server;

    public CallRMIServer() throws Exception {
        super();
    }

    public static boolean lookupLogin(String user, String pass) throws Exception {
        lookupServer();
        return server.checkLogin(user, pass);
    }

    public static int lookupCreateAccount(String fname, String user, String email, String pass) throws Exception {
        lookupServer();
        return server.createAccount(fname, user, email, pass);
    }

    public static void lookupServer() {
        try {
            server = (FileCatalogServer) Naming.lookup("rmi://localhost/" + FileCatalogServer.SERVER_NAME_IN_REGISTRY);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static int lookupDelete(String fileName) throws Exception {
        lookupServer();
        return server.deleteFile(fileName);
    }

    public static int lookupUpload(String fn, String ac, String on, String pr, String pth) throws IOException, Exception {
        Path fileLocation = Paths.get(pth);
        byte[] data = Files.readAllBytes(fileLocation);
        lookupServer();
        return server.uploadFile(fn, ac, data, on, pr);
    }

    @Override
    public void rcvMessage(String msg, String user) throws Exception {
        System.out.println(msg + " From: " + user);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id1212.homework3.adisu.rmi.server.startup;

import id1212.homework3.adisu.rmi.server.controller.FileCatalogServerController;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author ema
 */
public class Mian {

    public static void main(String arg[]) {
        try {
            new Mian().startRegistry();
            Naming.rebind(FileCatalogServerController.SERVER_NAME_IN_REGISTRY,
                    new FileCatalogServerController());
            System.out.println(">Server Started...");
        } catch (Exception ex) {
            System.err.println("Error...: " + ex);
        }

    }

    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException ex) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }
}

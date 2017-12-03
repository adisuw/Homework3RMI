/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.common;

import java.rmi.Remote;

/**
 *
 * @author ema
 */
public interface Messanger extends Remote{
    public void rcvMessage(String msg, String user) throws Exception;
}

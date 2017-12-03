/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1212.homework3.adisu.rmi.server.model;

import id1212.homework3.adisu.rmi.common.Messanger;

/**
 *
 * @author ema
 */
public class NotificationHandler {
    Messanger msg;
    String user;
    public NotificationHandler(Messanger msg, String user){
        this.msg = msg;
        this.user = user;
    }
    public void sendMessage(String msg, String user) throws Exception{
        this.msg.rcvMessage(msg, user);
    }
}

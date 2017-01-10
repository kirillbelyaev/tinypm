/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
 * Colorado State University
 * Department of Computer Science,
 * Fort Collins, CO  80523-1873, USA
 *
 * E-mail contact:
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 *   
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
package edu.csu.lpm.machine;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * This provide remote connectivity so it will require remote client to connect
 * @author maalv
 */
public class ServerMain {

    

    /**
     * The set of all the print writers for all the client. This set is kept so
     * we can easily broadcast messages.
     */
    static Map<String/*IP address*/, DataOutputStream> client = new HashMap<String, DataOutputStream>();
    private static Socket currentClient = null;

    public static void main(String[] args) throws Exception {
        // Prints the current systems IP address
        InetAddress iAddress = InetAddress.getLocalHost();
        String server_IP = iAddress.getHostAddress();
        System.out.println("Server IP address : " +server_IP);
        ServerSocket listener = new ServerSocket(RemoteConnect.PORT);
        try {
            while (true) {
                if(client.size() == 0) {
                	currentClient = listener.accept();
                    new ServerHandler(currentClient).start();
                } else {
                    System.out.println("Other client trying to connect");
                }
            }
        } finally {
            listener.close();
        }
    }
    
    public static void removeCurrentClient(){
    	client.remove(currentClient.getInetAddress().toString());
    	currentClient = null;
    }
}

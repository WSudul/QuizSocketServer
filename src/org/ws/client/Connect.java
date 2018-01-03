package org.ws.client;



import org.ws.communication.RequestQuizListMessage;

import java.io.*;
import java.net.*;


public class Connect {
    private ObjectOutputStream output=null;
    private ObjectInputStream input=null;
    int port=8081;

    Connect(){
            try {
                InetAddress address = InetAddress.getLocalHost();
                System.out.println("address is "+ address);
                String hostIP = address.getHostAddress() ;
                System.out.println("ip is "+ hostIP);
                String hostName = address.getHostName();
                System.out.println("hostname is " +hostName);

                Socket socket = new Socket("192.168.43.100", port);//InetAddress.getByName(hostName, port));
                System.out.println("socket opened");
                try {
                    System.out.println("im inside try");

                    output = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("output updated");
                    input = new ObjectInputStream(socket.getInputStream());
                    System.out.println("input updated");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("I'm here");
                output.writeObject(new RequestQuizListMessage(null, hostIP));
                System.out.println("request sent");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    public static void main(String args[]) {
        new Connect();
    }
    }

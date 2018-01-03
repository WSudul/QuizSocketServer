package org.ws.client;

import org.ws.communication.RequestQuizesMessage;
import java.io.*;
import java.net.*;

public class Connect {

    protected ObjectOutputStream output = null;
    protected ObjectInputStream input = null;
    int port = 8081;
    private Socket socket=null;

    Connect() {
        try {
                InetAddress address = InetAddress.getLocalHost();
                System.out.println("address is " + address);
                String hostIP = address.getHostAddress();
                System.out.println("ip is " + hostIP);
                String hostName = address.getHostName();
                System.out.println("hostname is " + hostName);
                socket = new Socket("192.168.43.100", port);//InetAddress.getByName(hostName, port));
                System.out.println("socket opened");
                try {
                    System.out.println("try entered");
                    setOutput(new ObjectOutputStream(socket.getOutputStream()));
                    setInput(new ObjectInputStream(socket.getInputStream()));
                } catch (IOException e) {
                    System.out.println("catch entered");
                    System.out.println(e.getMessage());
                    performClose();
                    return;
                }
                System.out.println("try left");
                RequestsHandling req= new RequestsHandling(output);
                /////function choice here
                //output.writeObject(new RequestQuizesMessage(null, hostIP));
                //System.out.println("request " + this.getClass().getName() + " sent");


        } catch (Exception e) {
            System.out.println("catch is here");
            System.err.println(e);
            performClose();
        }
    }


    public ObjectInputStream getInput() {
        return input;
    }
    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    private void performClose() {
        try {
            socket.close();
            if (socket.isClosed()) System.out.println("closed connection to server");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




    public static void main(String args[]) {
        new Connect();
    }
}



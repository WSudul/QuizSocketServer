package org.ws.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Connect {

    protected ObjectOutputStream output = null;
    protected ObjectInputStream input = null;
    int port;
    String address;
    private Socket socket = null;

    public static void main(String args[]) {
        String address=null;
        try {
            address= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        new Connect(address,8081);
        System.out.println("end");;
    }



    public Connect(String address,int port) {
        this.address=address;
        this.port=port;
        System.out.println("Connect("+address+","+port+")");
    }

    public void work() {


        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Exception caught when connecting "+e.getMessage());
            performClose();

        }
        System.out.println("socket opened");
            try {

                this.output=new ObjectOutputStream(socket.getOutputStream());
                this.input=new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Excpetion caught when creating Stream objects");
                System.out.println(e.getMessage());
                performClose();
                return;
            }
        System.out.println("Starting RequestsHandling");
        RequestsHandling req = new RequestsHandling(output, input);
        System.out.println(req.login("123test") ? "Login ok" : "Login failed");
        while (true) {
            System.out.println("Requesting Quiz List");
            System.out.println(req.requestQuizList().toString());
            System.out.println(req.startQuiz(1001l).toString());
            System.out.println("SLEEEPING");
            try {
                Thread.currentThread().sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    private void performClose() {

        try {
            socket.close();
            if (socket.isClosed())
                System.out.println("closed connection to server");
        } catch (IOException e) {
            System.out.println("Client cuaght exception when closing socket: "+ e.getMessage());
        }



    }
}



package org.ws.client;


import org.ws.communication.RequestQuizListMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Connect {

    protected ObjectOutputStream output = null;
    protected ObjectInputStream input = null;
    int port = 8081;
    private Socket socket = null;

    public static void main(String args[]) {
        new Connect();
        System.out.println("end");;
    }



    public Connect() {
    }

    public void work() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("address is " + address);
            String hostIP = address.getHostAddress();
            System.out.println("ip is " + hostIP);
            String hostName = address.getHostName();
            System.out.println("hostname is " + hostName);
            socket = new Socket(address.getHostAddress(), port);//InetAddress.getByName(hostName, port));
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
            RequestsHandling req = new RequestsHandling(output, input);
            System.out.println(req.login("123test") ? "Login ok" : "Login failed");
            System.out.println(req.requestQuizList().toString());
            System.out.println(req.startQuiz(1l).toString());
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

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    private void performClose() {
        try {
            socket.close();
            if (socket.isClosed()) System.out.println("closed connection to server");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("I'm here");
            try {
                output.writeObject(new RequestQuizListMessage( "client"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("request sent");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}



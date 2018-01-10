package org.ws.client.gui;

public class Main {
    public static void main(String[] args) {
        ClientApplication myRunnable = new ClientApplication("",10,"");
        Thread t = new Thread(myRunnable);
        t.start();
    }
}


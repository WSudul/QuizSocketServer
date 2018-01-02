package org.ws.gui;

public class Main {
    public static void main(String[] args) {
        Application myRunnable = new Application(10);
        Thread t = new Thread(myRunnable);
        t.start();
    }
}


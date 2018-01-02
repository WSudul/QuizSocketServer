package org.ws.server.worker;


import org.ws.communication.RequestQuizMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Worker implements Runnable {

    private final static Logger logger = Logger.getLogger(Worker.class.getName());

    private RequestQuizMessage request;
    private InputStream inputStream;
    private Socket client;

    public Worker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        logger.info("Worker: " + Thread.currentThread().getName() + " is started");
        //do stuff

        try {
             inputStream= client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}

package org.ws.server.worker;


import org.ws.server.communication.RequestQuizMessage;

import java.net.Socket;
import java.util.logging.Logger;

public class Worker implements Runnable {

    private final static Logger logger = Logger.getLogger(Worker.class.getName());

    private RequestQuizMessage request;

    private Socket client;

    public Worker(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        logger.info("Worker: " + Thread.currentThread().getName() + " is started");
        //do stuff
    }

}

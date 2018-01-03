package org.ws.server.worker;


import org.ws.communication.*;
import org.ws.communication.job.Question;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

public class Worker implements Runnable {

    private final static Logger logger = Logger.getLogger(Worker.class.getName());

    private RequestQuizMessage request;
    private InputStream inputStream;
    private Socket client;
    private String clientName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean shouldRead=true;
    private String workerName;

    public Worker(Socket client) {
        this.client = client;
        this.workerName ="server "+Thread.currentThread().getName();
    }

    @Override
    public void run() {
        logger.info("Worker: " + Thread.currentThread().getName() + " is started");
        //do stuff

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            logger.warning(e.getMessage());
            performClose();
            return;
        }
        try {
            //#TODO receive request;
            while(shouldRead){
                logger.info(Thread.currentThread().toString());
                Object receivedMessage = input.readObject();
                if (!isValidMessage(receivedMessage)) {
                    output.writeObject(new RejectionMessage(workerName, null, "Unrecognized message received!"));
                } else {
                    //#TODO handle messages and responses
                    SocketMessage name= (SocketMessage) receivedMessage;
                    clientName=((SocketMessage) receivedMessage).getAuthor();

                    if(!addClientToList(clientName)){
                        output.writeObject(new EndCommunicationMessage(workerName));
                    }

                    SocketMessage message=null;

                    if(receivedMessage instanceof RequestQuizListMessage){
                        QuizListMessage payload = new QuizListMessage(workerName);
                        payload.setQuizes(new ArrayList<>());
                        message=payload;

                    }else if(receivedMessage instanceof RequestQuizMessage){

                    }else if(receivedMessage instanceof QuizAnswersMessage){

                    }else if(receivedMessage instanceof EndCommunicationMessage){
                        performClose();
                    }else
                    {
                        output.writeObject(new RejectionMessage(workerName, null, "Unhandled message type received"));
                    }









                }

            }

        } catch (IOException e) {

            performClose();
            return;
        } catch (ClassNotFoundException e) {
            logger.warning(e.getMessage());
            performClose();
            return;
        }
    }

    //#TODO access shared list between all threads
    private boolean addClientToList(String clientName) {


        return true;
    }

    private void performClose() {
        try {
            client.close();
            if(client.isClosed())
                logger.info("Closed connection with client: "+clientName);
        } catch (IOException e) {
           logger.warning(e.getMessage());
        }


    }

    private boolean isValidMessage(Object message){
        return message instanceof SocketMessage;

    }

}

package org.ws.server.worker;


import org.ws.communication.QuizMessage;
import org.ws.communication.RejectionMessage;
import org.ws.communication.RequestQuizMessage;
import org.ws.communication.SocketMessage;
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
                Object message = input.readObject();
                if (!isValidMessage(message)) {
                    output.writeObject(new RejectionMessage(workerName, null, "Unrecognized message received!"));
                } else {
                    //#TODO handle messages and responses
                    SocketMessage name= (SocketMessage) message;
                    clientName=((SocketMessage) message).getAuthor();

                    QuizMessage quizMessage=new QuizMessage(workerName);
                    quizMessage.setQuizId(1l);
                    quizMessage.setQuestions(new ArrayList<Question>());
                    output.writeObject(quizMessage);


                    input.readObject();


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

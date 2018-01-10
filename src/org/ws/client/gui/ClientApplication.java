package org.ws.client.gui;

import org.ws.client.RequestsHandling;
import org.ws.communication.job.Answer;
import org.ws.communication.job.Question;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ClientApplication implements Runnable {

    private MainWindow window;

    private Integer port;
    private String address;
    private Connection connection;
    private Socket socket;
    private String clientName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private RequestsHandling handler;

    public ClientApplication(String address, Integer port, String clientName) {
        this.port = port;
        this.address = address;
        this.clientName = clientName;

    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

//    public void run(){
//        try {
//            window=new MainWindow(address,port,clientName);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void run() {
            try {
                socket = new Socket(address, port);
            } catch (IOException e) {
                System.out.println("Exception caught when connecting "+e.getMessage());
                return;

            }
            System.out.println("socket opened");
            try {

                this.output=new ObjectOutputStream(socket.getOutputStream());
                this.input=new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Excpetion caught when creating Stream objects");
                System.out.println(e.getMessage());
                return;
            }

            System.out.println("Starting RequestsHandling");
            handler = new RequestsHandling(output, input);
            System.out.println(handler.login(clientName) ? "Login ok" : "Login failed");
            boolean should_loop=true;
            while (should_loop) {
                System.out.println("Requesting Quiz List");
                System.out.println(handler.requestQuizList().toString());
                List<Question> questions = handler.startQuiz(1001l);

                if(questions==null)
                    handler.endCommunication();

                System.out.println(questions.toString());

                System.out.println("SLEEEPING");
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    handler.endCommunication();
                    return;
                }

                for(Question question:questions)
                {
                    System.out.println("QUESTION:\n"+
                            question.getId()+" "+question.getQuestion()
                            +question.getPossibleAnswers().toString());

                    Answer answer=new Answer(question.getId(),new ArrayList<>(question.getPossibleAnswers().keySet()));
                    handler.sendAnswer(answer);
                }

                should_loop=false;

            }

       // startGUI();
    }
}

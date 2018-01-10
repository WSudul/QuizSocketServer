package org.ws.client.gui;

import org.ws.client.RequestsHandling;
import org.ws.communication.job.Answer;
import org.ws.communication.job.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainWindow {
    private JTextField textField1;
    private JPanel panel1;
    private JButton loginButton;
    private JTextArea textArea1;
    private JComboBox comboBox1;
    private JTextArea textArea2;
    private JComboBox<Long> quizListComboxBox;


    private Integer port;
    private String address;
    private Connection connection;
    private Socket socket;
    private String clientName;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private RequestsHandling handler;
    private List<Long> quizList;

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public MainWindow(String address, Integer port, String clientName) throws IOException {
        this.port = port;
        this.address = address;
        this.clientName = clientName;
        if(!configure())
            throw new IOException("Failed to configure object");

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (handler.login(textField1.getText())) {
                    textArea1.append("Login OK");
                    textField1.setVisible(false);
                    loginButton.setVisible(false);
                    quizList = handler.requestQuizList();
                    textArea1.append("Avaiable quzies: " + quizList.toString());

                        quizListComboxBox= new JComboBox<Long>((Long[]) quizList.toArray());

                } else {
                    textArea1.append("Login NOT OK");
                }
            }

        });

        quizListComboxBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Question> list = handler.startQuiz((Long) quizListComboxBox.getSelectedItem());
                textArea1.setVisible(false);
                textArea2.repaint();
                String text=new String();
                for(Question question:list)
                {
                    text+=question.getId()+". "+question.getQuestion()+"\n";
                    for(Long answer_id:question.getPossibleAnswers().keySet())
                        text+=answer_id+ question.getPossibleAnswers().get(answer_id)+ "\n";
                }
                textArea2.append("Questions: \n"+text);

            }
        });
    }

    private boolean configure(){
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Exception caught when connecting " + e.getMessage());
            return false;

        }
        System.out.println("socket opened");
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Excpetion caught when creating Stream objects");
            System.out.println(e.getMessage());
            return false;
        }

        System.out.println("Starting RequestsHandling");
        handler = new RequestsHandling(output, input);

        return true;
    }


    public void run() {
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Exception caught when connecting " + e.getMessage());
            return;

        }
        System.out.println("socket opened");
        try {

            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Excpetion caught when creating Stream objects");
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Starting RequestsHandling");
        handler = new RequestsHandling(output, input);
        System.out.println(handler.login(clientName) ? "Login ok" : "Login failed");
        boolean should_loop = true;
        while (should_loop) {
            System.out.println("Requesting Quiz List");
            System.out.println(handler.requestQuizList().toString());
            List<Question> questions = handler.startQuiz(1001l);

            if (questions == null)
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

            for (Question question : questions) {
                System.out.println("QUESTION:\n" +
                        question.getId() + " " + question.getQuestion()
                        + question.getPossibleAnswers().toString());

                Answer answer = new Answer(question.getId(), new ArrayList<>(question.getPossibleAnswers().keySet()));
                handler.sendAnswer(answer);
            }

            should_loop = false;

        }


    }
}
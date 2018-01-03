package org.ws.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class launchQuiz extends Container {
    protected JPanel panel1, panel2;
    private JTextField niuField;
    private JButton loginButton;
    private JLabel niuLabel;
    private String niu;
    private JFrame frame,framewelcome;


    public void setNiu(String niu) {
        this.niu = niu;
    }

    public String getNIU() {
        return niu;
    }

    launchQuiz() {
        //this.framewelcome=frame;
        frame = new JFrame("Quiz application");
        //launchQuiz start=new launchQuiz();
        frame.setContentPane(panel1);
        frame.getContentPane().setPreferredSize(new Dimension(1300, 700));
        frame.pack();
        Application.centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        /*add (panel1);
        add(niuField);
        add(loginButton);*/
        //panel2.setVisible(false);
        niuField.setPreferredSize(new Dimension(100, 50));
        panel1.setVisible(true);
        niuField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNiu(niuField.getText());
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("succes" + getNIU());
                // panel1.add(myl, BorderLayout.SOUTH);
                panel1.revalidate();
                panel1.setVisible(false);
                //panel2.setVisible(true);
                openWelcome();

            }
        });
    }

        private void openWelcome(){
            quizWindow qui=new quizWindow();
//todo try with repaint
            //frame.setContentPane(start.panel2);
            //panel2.add(new Label("hi"));
            //panel2.setVisible(true);



    }

}

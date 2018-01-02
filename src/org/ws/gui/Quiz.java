package org.ws.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz extends Container {
    protected JPanel panel1, panel2;
    private JTextField niuField;
    private JButton loginButton;
    private JLabel niuLabel;
    private String niu;

    public void setNiu(String niu) {
        this.niu = niu;
    }

    public String getNIU() {
        return niu;
    }

    Quiz() {
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

            //frame.setContentPane(start.panel2);
            //panel2.add(new Label("hi"));
            //panel2.setVisible(true);



    }

}

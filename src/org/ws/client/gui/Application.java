package org.ws.client.gui;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {

    public JFrame frame;
    private int var;

    public Application(int var) {
        this.var = var;
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public JFrame getFrame() {
        return frame;
    }

    private void start() {
      /*  frame = new JFrame("launchQuiz application");
        launchQuiz start=new launchQuiz(frame);
        frame.setContentPane(start.panel1);
        frame.getContentPane().setPreferredSize(new Dimension(1300, 700));
        frame.pack();
        centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/
        launchQuiz myquiz = new launchQuiz();
    }

    public void run() {
        start();
    }
}

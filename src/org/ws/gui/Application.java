package org.ws.gui;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {

    private int var;
    public JFrame frame;
    public Application(int var) {
        this.var = var;
    }

    private void start(){
        frame = new JFrame("Quiz application");
        Quiz start=new Quiz();
        frame.setContentPane(start.panel1);
        frame.getContentPane().setPreferredSize(new Dimension(1300, 700));
        frame.pack();
        centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    public void run() {
        start();
    }
}

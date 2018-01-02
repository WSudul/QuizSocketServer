package org.ws.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowtobedeleted {
    protected JPanel panel1;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JLabel Label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel niuLabel;
    private JTextField niuField;
    private JButton loginButton;
    private String niu;
    private CardLayout cardLayout = new CardLayout();

   /* CardLayout card = (CardLayout) panel1.getLayout();
    card.show(panel1, "panelOne");*/

    public MainWindowtobedeleted(){
        panel1=new JPanel();
        panel1.setLayout(cardLayout);
        panelOne=new JPanel();
        panelThree=new JPanel();
        panelTwo=new JPanel();
        panelOne.add(Label1);
        panelTwo.add(label2);
        panelThree.add(label3);
        panel1.add(panelOne, "1");
        panel1.add(panelTwo, "2");
        panel1.add(panelThree, "3");
     //   panelOne.add (panel1);
        panelTwo.add(niuField);
        panelThree.add(loginButton);
        niuField.setPreferredSize( new Dimension( 100, 50 ) );
        panel1.setVisible(true);

        niuField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                niu = niuField.getText();
            }
//  cardLayout.show(cardPanel, "1");
    });
    }


/*public static void main(String[] args) {
        JFrame frame = new JFrame("launchQuiz application");
        CardLayout card = (CardLayout)panel1.getLayout();
        card.show(panel1, "panelOne");
        //launchQuiz start=new launchQuiz();
        //frame.setContentPane(start.panel1);
        frame.getContentPane().setPreferredSize(new Dimension(1300, 700));
        frame.pack();
        centreWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
*/
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("launchQuiz application");
                MainWindowtobedeleted frame2 = new MainWindowtobedeleted();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setPreferredSize(new Dimension(1300, 700));
                frame.pack();
                centreWindow(frame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setVisible(true);
            }
        });
    }
}
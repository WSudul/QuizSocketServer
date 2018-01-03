package org.ws.client.gui;

import javax.swing.*;
import java.util.List;

public class quizWindow {

    private JPanel panel2;
    private JComboBox comboBox1;
    List<String> quizes;
    //SortedComboBoxModel model = new SortedComboBoxModel(items);

    quizWindow(){
        QuizesComboBox box=new QuizesComboBox();
        comboBox1=box.getComboBox();
        panel2.add(comboBox1);

    }

}

package org.ws.client.gui;

import javax.swing.*;
import java.util.List;

public class quizWindow {

    List<String> quizes;
    private JPanel panel2;
    private JComboBox comboBox1;
    //SortedComboBoxModel model = new SortedComboBoxModel(items);

    quizWindow() {
        QuizesComboBox box = new QuizesComboBox();
        comboBox1 = box.getComboBox();
        panel2.add(comboBox1);

    }

}

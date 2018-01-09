package org.ws.client.gui;

import org.ws.communication.QuizListMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class QuizesComboBox {

    private JComboBox comboBox;
    private JTextField textField;
    public QuizesComboBox() {
        textField = new JTextField(15);
        textField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox.addItem(textField.getText());
                textField.setText("");
                comboBox.showPopup();
            }
        });

        QuizListMessage qu = new QuizListMessage("TEST");
        List<Long> quizes = qu.getQuizes();
        System.out.println("Is quizes null:?"); if (quizes==null) {System.out.println(" yes");} else System.out.println(" no");;
        System.out.println(quizes.toString());
        SortedComboBoxModel model =
                new SortedComboBoxModel(
                        quizes.stream()
                                .map(id->id.toString())
                                .collect(Collectors.toList()));
        comboBox = new JComboBox(model);
        comboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    private class SortedComboBoxModel extends DefaultComboBoxModel {

        public SortedComboBoxModel() {
            super();
        }


        public SortedComboBoxModel(List<String> items) {
            Collections.sort(items);
            int size = items.size();
            for (int i = 0; i < size; i++) {
                super.addElement(items.get(i));
            }
            setSelectedItem(items.get(0));
        }

        @Override
        public void addElement(Object element) {
            insertElementAt(element, 0);
        }

        @Override
        public void insertElementAt(Object element, int index) {
            int size = getSize();
            //  Determine where to insert element to keep model in sorted order
            for (index = 0; index < size; index++) {
                Comparable c = (Comparable) getElementAt(index);
                if (c.compareTo(element) > 0) {
                    break;
                }
            }
            super.insertElementAt(element, index);
        }
    }
}

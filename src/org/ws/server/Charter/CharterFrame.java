package org.ws.server.Charter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CharterFrame extends JFrame {


    private Label labelInfo;
    private JTable jTable;
    MyChart myChart;
    private Long quizId;
    private Map<Long,List<Long>> data;

    public CharterFrame() throws HeadlessException {
        this.setTitle("Charter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.prepareUI();
        this.pack();
        this.setVisible(true);
    }


    public void getData(Long quizId,Map<Long,List<Long>> questionAnswersCount){
        this.quizId=quizId;
        this.data=questionAnswersCount;
        System.out.println(questionAnswersCount.toString());
        myChart.updateList(questionAnswersCount);
        labelInfo.setText("QUIZ="+quizId.toString());
    }

    private void prepareUI(){

        JPanel vPanel = new JPanel();
        vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));

        myChart = new MyChart();
        myChart.setPreferredSize(new Dimension(450, 200));


        JScrollPane jScrollPane = new JScrollPane(myChart);
        jScrollPane.setPreferredSize(new Dimension(450, 100));
        vPanel.add(jScrollPane);

        labelInfo = new Label();
        vPanel.add(labelInfo);

        getContentPane().add(myChart, BorderLayout.PAGE_START);
        getContentPane().add(vPanel, BorderLayout.CENTER);

    }


    private class MyChart extends JComponent {
        Map<Long,List<Long>> data;

        public void updateList(Map<Long,List<Long>> data){
            this.data = data;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(data != null){
                paintMe(g);
            }
        }

        private void paintMe(Graphics graphics){
            Graphics2D graph = (Graphics2D)graphics;
            int width = getWidth();
            int height = getHeight();
            graph.drawRect(0,100,100,10);
            graph.drawRect(100,0,10,80);
            graph.drawRect(width,0,10,10);
            graph.drawRect(0,height,10,10);
            int hDiv = Math.round((width/(float)(data.size())));

            for(Long key: data.keySet())
            {
                int i=0;
                data.get(key);
                for (Long count:data.get(key)) {
                    graph.setColor(Color.black);
                    System.out.println(i * hDiv+" "+ 10*Math.toIntExact(count));
                    graph.drawRect(i * hDiv, height-10*Math.toIntExact(count), 10*Math.toIntExact(count),hDiv);
                    graph.setColor(Color.gray);
                    graph.fillRect(i * hDiv,height- 10*Math.toIntExact(count), 10*Math.toIntExact(count),hDiv);
                    ++i;
                }
            }

            graph.drawRect(0, 0, width, height);
        }

    }

}

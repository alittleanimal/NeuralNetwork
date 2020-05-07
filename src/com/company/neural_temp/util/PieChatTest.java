package com.company.neural_temp.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class PieChatTest extends ApplicationFrame {
    public PieChatTest(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IPhone 5s", new Double(20));
        dataset.setValue( "SamSung Grand" , new Double( 20 ) );
        dataset.setValue( "MotoG" , new Double( 40 ) );
        dataset.setValue( "Nokia Lumia" , new Double( 10 ) );
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",
                dataset,
                true,
                true,
                false
        );
        return chart;
    }

    private JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        PieChatTest chatDemo = new PieChatTest("Mobile Sales");
        chatDemo.setSize(560, 367);
        RefineryUtilities.centerFrameOnScreen(chatDemo);
        chatDemo.setVisible(true);
    }
}

package com.company.neural_temp.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.time.Year;

public class JfreeChatTest {

    private void testPieChart() throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IPhone 5s", new Double(20));
        dataset.setValue("SamSung Grand", new Double(20));
        dataset.setValue("MotoG", new Double(40));
        dataset.setValue("Nokia Lumia", new Double(10));

        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",
                dataset,
                true,
                true,
                false
        );

        // Show chart
        showChart(chart, "Pie Chart Test");

        // Save chart to JPEG file
//        saveChartJpeg(chart, "PieChart.jpeg");
    }

    private void testBarChart() {
        final String fiat = "FIAT";
        final String audi = "AUDI";
        final String ford = "FORD";
        final String speed = "Speed";
        final String millage = "Millage";
        final String userrating = "User Rating";
        final String safety = "safety";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, fiat, speed);
        dataset.addValue(3.0, fiat, userrating);
        dataset.addValue(5.0, fiat, millage);
        dataset.addValue(5.0, fiat, safety);

        dataset.addValue(5.0, audi, speed);
        dataset.addValue(6.0, audi, userrating);
        dataset.addValue(10.0, audi, millage);
        dataset.addValue(4.0, audi, safety);

        dataset.addValue(4.0, ford, speed);
        dataset.addValue(2.0, ford, userrating);
        dataset.addValue(3.0, ford, millage);
        dataset.addValue(6.0, ford, safety);

        JFreeChart barChart = ChartFactory.createBarChart(
                "Car Usage Statistics",
                "Category",
                "Score",
                dataset, PlotOrientation.VERTICAL,
                true, true, false
        );

        showChart(barChart, "BarChart Test");
    }

    private void testLineChart() {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        line_chart_dataset.addValue(15, "schools", "1970");
        line_chart_dataset.addValue(30, "schools", "1980");
        line_chart_dataset.addValue(60, "schools", "1990");
        line_chart_dataset.addValue(120, "schools", "2000");
        line_chart_dataset.addValue(240, "schools", "2010");
        line_chart_dataset.addValue(300, "schools", "2014");

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "School Vs Years", "Year",
                "Schools Count",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false
        );

        showChart(lineChartObject, "LineChart Test");

    }

    private void testXYLineChart() {
        XYSeries firefox = new XYSeries("Firefox");
        firefox.add(1.0, 1.0);
        firefox.add(2.0, 4.0);
        firefox.add(3.0, 3.0);

        XYSeries chrome = new XYSeries("Chrome");
        chrome.add(1.0, 4.0);
        chrome.add(2.0, 5.0);
        chrome.add(3.0, 6.0);

        XYSeries iexplorer = new XYSeries("InternetExplorer");
        iexplorer.add(3.0, 4.0);
        iexplorer.add(4.0, 5.0);
        iexplorer.add(5.0, 4.0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(firefox);
        dataset.addSeries(chrome);
        dataset.addSeries(iexplorer);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Browser usage statastics",
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        showChart(xylineChart, "XYLineChart Test");
    }

    public static void main(String[] args) throws IOException {
        JfreeChatTest jFreeChart = new JfreeChatTest();
//        jFreeChart.testPieChart();
//        jFreeChart.testBarChart();
//        jFreeChart.testLineChart();
        jFreeChart.testXYLineChart();
    }

    private void showChart(JFreeChart chart, String chartName) {
        ChartFrame frame = new ChartFrame(chartName, chart);
        frame.pack();
        frame.setVisible(true);
    }

    private void saveChartJpeg(JFreeChart chart, String fileName, int width, int height) throws IOException {
        File pieChart = new File(fileName);
        ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
    }

    private void saveChartJpeg(JFreeChart chart, String fileName) throws IOException {
        File pieChart = new File(fileName);
        ChartUtilities.saveChartAsJPEG(pieChart, chart, 640, 480);
    }
}

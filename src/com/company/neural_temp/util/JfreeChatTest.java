package com.company.neural_temp.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

        // BarChart 3D
//        JFreeChart barChart = ChartFactory.createBarChart3D(
//                "Car Usage Statistics",
//                "Category",
//                "Score",
//                dataset, PlotOrientation.VERTICAL,
//                true, true, false
//        );

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

    private void testPieChart3D() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IPhone 5s", new Double(20));
        dataset.setValue("SamSung Grand", new Double(20));
        dataset.setValue("MotoG", new Double(40));
        dataset.setValue("Nokia Lumia", new Double(10));

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Mobile Sales", dataset,
                true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(170);
        plot.setForegroundAlpha(0.70f);
        plot.setInteriorGap(0.05);

        showChart(chart, "PieChart 3D Test");
    }

    private void testPieChartFile() throws IOException {
        String mobilebrands[] = {
                "IPhone 5s",
                "SamSung Grand",
                "MotoG",
                "Nokia Lumia"
        };

//        InputStream in = new FileInputStream(defineAbsoluteFilePath("src/data", "JfreeChartTest.txt"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        BufferedReader reader = new BufferedReader(new FileReader(defineAbsoluteFilePath("src/data", "JfreeChartTest.txt")));

        try {
            StringBuilder out = new StringBuilder();
            String line;
            DefaultPieDataset dataset = new DefaultPieDataset();

            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(System.lineSeparator());
            }

            Scanner scanner = new Scanner(out.toString());
            int i = 0;
            while (scanner.hasNextLine() && mobilebrands[i] != null) {
                String[] strVector = scanner.nextLine().split(",");
                dataset.setValue(mobilebrands[i], Double.parseDouble(strVector[1]));
                i++;
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Mobile Sales", dataset,
                    true, true, false
            );

            showChart(chart, "PieChart Test From File");
        } finally {
            reader.close();
        }
    }

    private void testTimeSeriesChart() {
        TimeSeries series = new TimeSeries("Random data", Second.class);
        Second current = new Second();
        double value = 100.0;

        for (int i = 0; i < 1000; i++) {
            try {
                value = value + Math.random() - 0.5;
                series.add(current, new Double(value));
                current = (Second) current.next();
            } catch (SeriesException e) {
                System.err.println("Error adding to series");
            }
        }

        XYDataset dataset = new TimeSeriesCollection(series);
        JFreeChart timechart = ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Second",
                "Value",
                dataset, true, true, false
        );

        showChart(timechart, "TimeSeries Chart Test");
    }

    public static void main(String[] args) throws IOException {
        JfreeChatTest jFreeChart = new JfreeChatTest();
        jFreeChart.testPieChart();
        jFreeChart.testBarChart();
        jFreeChart.testLineChart();
        jFreeChart.testXYLineChart();
        jFreeChart.testPieChart3D();
        jFreeChart.testPieChartFile();
        jFreeChart.testTimeSeriesChart();
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

    private String defineAbsoluteFilePath(String path, String fileName) {
        String absoluteFilePath = "";

        String workingDir = System.getProperty("user.dir");
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.indexOf("win") >= 0) {
            absoluteFilePath = workingDir + "\\" + path + "\\" + fileName;
        } else {
            absoluteFilePath = workingDir + "/" + path + "/" + fileName;
        }

        File file = new File(absoluteFilePath);

        if (file.exists()) {
            System.out.println("File found!");
            System.out.println(absoluteFilePath);
        } else {
            System.out.println("File did not find...");
        }

        return absoluteFilePath;
    }
}

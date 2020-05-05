package com.company.neural_temp.util;

import java.io.IOException;

public class ChartTest {
    public static void main(String[] args) {
        Data d = new Data();
        d.setPath("src/data");
        d.setFileName("01_12_2014_Belem_v1.csv");

        try{
            double[][] matrix = d.rawData2Matrix(d);
            Chart c = new Chart();
            c.plotXYData(matrix, "Input Data", "Data", "Value");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

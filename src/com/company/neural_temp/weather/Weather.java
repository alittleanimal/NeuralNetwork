package com.company.neural_temp.weather;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.util.Chart;
import com.company.neural_temp.util.Data;

import java.io.IOException;
import java.util.ArrayList;

public class Weather {
    public static void main(String args[]) {

        Data weatherDataInput = new Data("src/data/weather", "inmet_13_14_input.csv");
        Data weatherDataOutput = new Data("src/data/weather", "inmet_13_14_output.csv");

        Data weatherDataInputTestRNA = new Data("src/data/weather", "inmet_13_14_input_test.csv");
        Data weatherDataOutputTestRNA = new Data("src/data/weather", "inmet_13_14_output_test.csv");

        Data.NormalizationTypesENUM NORMALIZATION_TYPE = Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED;

        try {
            double[][] matrixInput = weatherDataInput.rawData2Matrix(weatherDataInput);
            double[][] matrixOutput = weatherDataOutput.rawData2Matrix(weatherDataOutput);

            double[][] matrixInputTestRNA = weatherDataInputTestRNA.rawData2Matrix(weatherDataInputTestRNA);
            double[][] matrixOutputTestRNA = weatherDataOutputTestRNA.rawData2Matrix(weatherDataOutputTestRNA);

            double[][] matrixInputNorm = weatherDataInput.normalize(matrixInput, NORMALIZATION_TYPE);
            double[][] matrixOutputNorm = weatherDataOutput.normalize(matrixOutput, NORMALIZATION_TYPE);

            double[][] matrixInputTestRNANorm = weatherDataInputTestRNA.normalize(matrixInputTestRNA, NORMALIZATION_TYPE);
            double[][] matrixOutputTestRNANorm = weatherDataOutputTestRNA.normalize(matrixOutputTestRNA, NORMALIZATION_TYPE);

            NeuralNet n1 = new NeuralNet();
            n1 = n1.initNet(4, 1, 4, 1);

            n1.setTrainSet(matrixInputNorm);
            n1.setRealMatrixOutputSet(matrixOutputNorm);

            n1.setMaxEpochs(1000);
            n1.setTargetError(0.00001);
            n1.setLearningRate(0.5);
            n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
            n1.setActivationFnc(Training.ActivationFncEnum.SIGLOG);
            n1.setActivationFncOutputLayer(Training.ActivationFncEnum.LINEAR);

            NeuralNet n1Trained = new NeuralNet();
            n1Trained = n1.trainNet(n1);
            System.out.println();

            //ERROR:
            Chart c1 = new Chart();
            c1.plotXYData(n1.getListOfMSE().toArray(), "MSE Error", "Epochs", "Mse Value");

            //TRAINING:
            double[][] matrixOutputsJoined = prepareDataForChart(NORMALIZATION_TYPE, matrixOutput, n1Trained);

            Chart c2 = new Chart();
            c2.plotXYData(matrixOutputsJoined, "Real x Estimated - Training Data", "Weather Data", "Temperature (Celsius)", Chart.ChartPlotTypeENUM.COMPARISON);

            //TEST:
            n1Trained.setTrainSet(matrixInputTestRNANorm);
            n1Trained.setRealMatrixOutputSet(matrixOutputTestRNANorm);

            double[][] matrixOutputsJoinedTest = prepareDataForChart(NORMALIZATION_TYPE, matrixOutputTestRNA, n1Trained);

            Chart c3 = new Chart();
            c3.plotXYData(matrixOutputsJoinedTest,"Real x Estimated - Test Data", "Weather Data", "Temperature (Celsius)", Chart.ChartPlotTypeENUM.COMPARISON);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double[][] prepareDataForChart(Data.NormalizationTypesENUM type, double[][] output, NeuralNet n1Trained) {
        double[][] matrixOutputRNATest = n1Trained.getNetOutputValues(n1Trained);
        double[][] matrixOutputRNADenormTest = new Data().denormalize(output, matrixOutputRNATest, type);

        ArrayList<double[][]> listOfArraysToJoinTest = new ArrayList<>();
        listOfArraysToJoinTest.add(output);
        listOfArraysToJoinTest.add(matrixOutputRNADenormTest);

        return new Data().joinArrays(listOfArraysToJoinTest);
    }
}

package com.company.neural_temp.ocr;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.util.Chart;
import com.company.neural_temp.util.Data;

import java.io.IOException;

public class DigitOnline {

    public static void main(String[] args) {
        Data ocrDataInput = new Data("src/data/ocr", "ocr_training_inputs.csv");
        Data ocrDataOutput = new Data("src/data/ocr", "ocr_training_outputs.csv");

        Data ocrDataInputTestRNA = new Data("src/data/ocr", "ocr_test_inputs.csv");
        Data ocrDataOutputTestRNA = new Data("src/data/ocr", "ocr_test_outputs.csv");

        try {
            double[][] matrixInput = ocrDataInput.rawData2Matrix(ocrDataInput);
            double[][] matrixOutput = ocrDataOutput.rawData2Matrix(ocrDataOutput);

            double[][] matrixInputTestRNA = ocrDataInputTestRNA.rawData2Matrix(ocrDataInputTestRNA);
            double[][] matrixOutputTestRNA = ocrDataOutputTestRNA.rawData2Matrix(ocrDataOutputTestRNA);

            NeuralNet n1 = new NeuralNet();
            n1 = n1.initNet(25, 1, 18, 10);

            n1.setTrainSet(matrixInput);
            n1.setRealMatrixOutputSet(matrixOutput);

            n1.setMaxEpochs(6000);
            n1.setTargetError(0.00001);
            n1.setLearningRate(1.0);
            n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION_ONLINE);
            n1.setLearningRatePercentageReduce(0.01);
            n1.setActivationFnc(Training.ActivationFncEnum.SIGLOG);
            n1.setActivationFncOutputLayer(Training.ActivationFncEnum.SIGLOG);

            NeuralNet n1Trained = new NeuralNet();
            n1Trained = n1.trainNet(n1);

            //ERROR
            Chart c1 = new Chart();
            c1.plotXYData(n1.getListOfMSE().toArray(), "MSE Error", "Epochs", "MSE Value");

//            //Test
//            n1Trained.setTrainSet(matrixInput);
//            n1Trained.setRealMatrixOutputSet(matrixOutput);
//
//            double[][] matrixOutputRNATraining = n1Trained.getNetOutputValues(n1Trained);
//            System.out.println();
//
//            System.out.println("Real output [training]: ");
//            printArray(matrixOutput, 0, 10);
//
//            System.out.println("Estimated output [training]: ");
//            printArray(matrixOutputRNATraining, 0, 10);
//
//            //TEST (test data):
//            n1Trained.setTrainSet( matrixInputTestRNA );
//            n1Trained.setRealMatrixOutputSet( matrixOutputTestRNA );
//
//            double[][] matrixOutputRNATest = n1Trained.getNetOutputValues( n1Trained );
//
//            System.out.println();
//
//            System.out.println("Real output [test]:");
//            printArray(matrixOutputTestRNA, 0, 10);
//
//            System.out.println("Estimated output [test]:");
//            printArray(matrixOutputRNATest, 0, 10);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printArray(double[][] array, int initRowToPrint, int lastRowToPrint) {
        for (int i = initRowToPrint; i < lastRowToPrint; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("%.2f", array[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}

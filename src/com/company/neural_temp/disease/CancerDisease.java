package com.company.neural_temp.disease;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.util.Chart;
import com.company.neural_temp.util.Classification;
import com.company.neural_temp.util.Data;

import java.io.IOException;
import java.util.ArrayList;

public class CancerDisease {
    public static void main(String[] args) {
        Data diseaseDataInput = new Data("src/data/disease", "breast_cancer_inputs_training.csv");
        Data diseaseDataOutput = new Data("src/data/disease", "breast_cancer_output_training.csv");

        Data diseaseDataInputTestRNA  = new Data("src/data/disease", "breast_cancer_inputs_test.csv");
        Data diseaseDataOutputTestRNA = new Data("src/data/disease", "breast_cancer_output_test.csv");

        Data.NormalizationTypesENUM NORMALIZATION_TYPE = Data.NormalizationTypesENUM.MAX_MIN;

        try{
            double[][] matrixInput = diseaseDataInput.rawData2Matrix(diseaseDataInput);
            double[][] matrixOutput = diseaseDataOutput.rawData2Matrix(diseaseDataOutput);

            double[][] matrixInputTestRNA = diseaseDataInputTestRNA.rawData2Matrix(diseaseDataInputTestRNA);
            double[][] matrixOutputTestRNA = diseaseDataOutputTestRNA.rawData2Matrix(diseaseDataOutputTestRNA);

            double[][] matrixInputNorm = diseaseDataInput.normalize(matrixInput, NORMALIZATION_TYPE);
            double[][] matrixInputTestRNANorm = diseaseDataOutput.normalize(matrixInputTestRNA, NORMALIZATION_TYPE);

            NeuralNet n1 = new NeuralNet();
            n1 = n1.initNet(9, 1, 5,1);

            n1.setTrainSet(matrixInputNorm);
            n1.setRealMatrixOutputSet(matrixOutput);

            n1.setMaxEpochs(1000);
            n1.setTargetError(0.00001);
            n1.setLearningRate(0.1);
            n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
            n1.setActivationFnc(Training.ActivationFncEnum.HYPERTAN);
            n1.setActivationFncOutputLayer(Training.ActivationFncEnum.SIGLOG);

            NeuralNet n1Trained = new NeuralNet();
            n1Trained = n1.trainNet(n1);
            System.out.println();

            //ERROR:
            Chart c1 = new Chart();
            c1.plotXYData(n1.getListOfMSE().toArray(), "MSE Error", "Epochs","MSE Value");

            n1Trained.setTrainSet(matrixInputTestRNANorm);
            n1Trained.setRealMatrixOutputSet(matrixOutputTestRNA);

            double[][] matrixOutputRNATest = n1Trained.getNetOutputValues(n1Trained);

            ArrayList<double[][]> listOfArraysToJoinTest = new ArrayList<>();
            listOfArraysToJoinTest.add(matrixOutputTestRNA);
            listOfArraysToJoinTest.add(matrixOutputRNATest);

            double[][] matrixOutputsJoinedTest = new Data().joinArrays(listOfArraysToJoinTest);

            Chart c2 = new Chart();
            c2.plotBarChart(matrixOutputsJoinedTest, "Real x Estimated - Test Data", "Breast Cancer Data", "Diagnosis (0: Ben/ 1: MAL");

            //CONFUSION MATRIX
            Classification classif = new Classification();

            double[][] confusionMatrix = classif.calculateConfusionMatrix(0.6, matrixOutputsJoinedTest);
            classif.printConfusionMatrix(confusionMatrix);

            //Sensitivity
            System.out.println("Sensitivity = " + classif.calculateSensitivity(confusionMatrix));

            //Specificity
            System.out.println("Specificity = " + classif.calculateSpecificity(confusionMatrix));

            //Accuracy
            System.out.println("Accuracy = " + classif.calculateAccuracy(confusionMatrix));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

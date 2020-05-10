package com.company.neural_temp.disease;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.util.Chart;
import com.company.neural_temp.util.Classification;
import com.company.neural_temp.util.Data;

import java.io.IOException;
import java.util.ArrayList;

public class DiabetesDisease {
    public static void main(String[] args) {
        Data diseaseDataInput  = new Data("src/data/disease", "diabetes_inputs_training.csv");
        Data diseaseDataOutput = new Data("src/data/disease", "diabetes_output_training.csv");

        Data diseaseDataInputTestRNA  = new Data("src/data/disease", "diabetes_inputs_test.csv");
        Data diseaseDataOutputTestRNA = new Data("src/data/disease", "diabetes_output_test.csv");

        Data.NormalizationTypesENUM NORMALIZATION_TYPE = Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED;

        try{
            double[][] matrixInput  = diseaseDataInput.rawData2Matrix( diseaseDataInput );
            double[][] matrixOutput = diseaseDataOutput.rawData2Matrix( diseaseDataOutput );

            double[][] matrixInputTestRNA  = diseaseDataOutput.rawData2Matrix( diseaseDataInputTestRNA );
            double[][] matrixOutputTestRNA = diseaseDataOutput.rawData2Matrix( diseaseDataOutputTestRNA );

            double[][] matrixInputNorm = diseaseDataInput.normalize(matrixInput, NORMALIZATION_TYPE);
            double[][] matrixInputTestRNANorm = diseaseDataInputTestRNA.normalize(matrixInputTestRNA, NORMALIZATION_TYPE);

            NeuralNet n1 = new NeuralNet();
            n1 = n1.initNet(8, 1, 3,2);

            n1.setTrainSet(matrixInputNorm);
            n1.setRealMatrixOutputSet(matrixOutput);

            n1.setMaxEpochs(1000);
            n1.setTargetError(0.00001);
            n1.setLearningRate(0.1);
            n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
            n1.setActivationFnc(Training.ActivationFncEnum.SIGLOG);
            n1.setActivationFncOutputLayer(Training.ActivationFncEnum.SIGLOG);

            NeuralNet n1Trained = new NeuralNet();

            n1Trained = n1.trainNet(n1);

            System.out.println();

            //ERROR:
            Chart c1 = new Chart();
            c1.plotXYData(n1.getListOfMSE().toArray(), "MSE Error", "Epochs", "MSE Value");

            Classification classif = new Classification();

            //TEST:
            n1Trained.setTrainSet( matrixInputTestRNANorm );
            n1Trained.setRealMatrixOutputSet( matrixOutputTestRNA );;

            double[][] matrixOutputRNATest = n1Trained.getNetOutputValues(n1Trained);

            if(n1Trained.getOutputLayer().getNumberOfNeuronsInLayer() > 1) {

                matrixOutputTestRNA = classif.convertToOneColumn(matrixOutputTestRNA);
                matrixOutputRNATest = classif.convertToOneColumn(matrixOutputRNATest);

            }

            ArrayList<double[][]> listOfArraysToJoinTest = new ArrayList<double[][]>();
            listOfArraysToJoinTest.add( matrixOutputTestRNA );
            listOfArraysToJoinTest.add( matrixOutputRNATest );

            double[][] matrixOutputsJoinedTest = new Data().joinArrays(listOfArraysToJoinTest);

            Chart c3 = new Chart();
            c3.plotBarChart(matrixOutputsJoinedTest, "Real x Estimated - Test Data", "Diabetes Data", "Diagnosis (0: NO / 1: YES)");

            //CONFUSION MATRIX
            double[][] confusionMatrix = classif.calculateConfusionMatrix(0.6, matrixOutputsJoinedTest);
            classif.printConfusionMatrix(confusionMatrix);

            //SENSITIVITY
            System.out.println("SENSITIVITY = " + classif.calculateSensitivity(confusionMatrix));

            //SPECIFICITY
            System.out.println("SPECIFICITY = " + classif.calculateSpecificity(confusionMatrix));

            //ACCURACY
            System.out.println("ACCURACY    = " + classif.calculateAccuracy(confusionMatrix));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

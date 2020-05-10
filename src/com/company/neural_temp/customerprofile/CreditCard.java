package com.company.neural_temp.customerprofile;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.som.Kohonen;
import com.company.neural_temp.util.Classification;
import com.company.neural_temp.util.Data;

import java.io.IOException;
import java.util.ArrayList;

public class CreditCard {

    public static void main(String[] args) {
        Data cardDataInput = new Data("src/data/card", "card_inputs_training.csv");

        Data cardDataInputTestRNA = new Data("src/data/card", "card_inputs_test.csv");
        Data cardDataOutputTestRNA = new Data("src/data/card", "card_output_test.csv");

        Data.NormalizationTypesENUM NORMALIZATION_TYPE = Data.NormalizationTypesENUM.MAX_MIN;

        try {
            double[][] matrixInput = cardDataInput.rawData2Matrix(cardDataInput);
            double[][] matrixInputTetsRNA = cardDataInputTestRNA.rawData2Matrix(cardDataInputTestRNA);
            double[][] matrixOutput = cardDataOutputTestRNA.rawData2Matrix(cardDataOutputTestRNA);

            double[][] matrixInputNorm = cardDataInput.normalize(matrixInput, NORMALIZATION_TYPE);
            double[][] matrixInputTestRNANorm = cardDataInputTestRNA.normalize(matrixInputTetsRNA, NORMALIZATION_TYPE);

            NeuralNet n1 = new NeuralNet();
            n1 = n1.initNet(10, 0,0,2);

            n1.setTrainSet(matrixInputNorm);
            n1.setValidationSet(matrixInputTestRNANorm);
            n1.setRealMatrixOutputSet(matrixOutput);

            n1.setMaxEpochs(1000);
            n1.setLearningRate(0.1);
            n1.setTrainType(Training.TrainingTypesENUM.KOHONEN);
            n1.setKohonenCaseStudy(Kohonen.KohonenCaseStudyENUM.CARD);

            NeuralNet n1Trained = new NeuralNet();
            n1Trained = n1.trainNet(n1);

            System.out.println();
            System.out.println("-----------KOHONEN TEST-----------");

            ArrayList<double[][]> listOfArraysToJoin = new ArrayList<>();
            double[][] matrixReal = n1Trained.getRealMatrixOutputSet();
            double[][] matrixEstimated = n1Trained.netValidation(n1Trained);

            listOfArraysToJoin.add(matrixReal);
            listOfArraysToJoin.add(matrixEstimated);

            double[][] matrixOutputsJoined = new Data().joinArrays(listOfArraysToJoin);

            //CONFUSION MATRIX
            Classification classif = new Classification();
            classif.getConfusionMatrixCommon(-1.0, matrixOutputsJoined);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

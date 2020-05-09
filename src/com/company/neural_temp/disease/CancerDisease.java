package com.company.neural_temp.disease;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.util.Chart;
import com.company.neural_temp.util.Data;

import java.io.IOException;

public class CancerDisease {
    public static void main(String[] args) {
        Data diseaseDataInput = new Data("src/data/disease", "brease_cancer_inputs_training.csv");
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
            n1.setLearningRate(0.9);
            n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
            n1.setActivationFnc(Training.ActivationFncEnum.SIGLOG);
            n1.setActivationFncOutputLayer(Training.ActivationFncEnum.SIGLOG);

            NeuralNet n1Trained = new NeuralNet();
            n1Trained = n1.trainNet(n1);
            System.out.println();

            //ERROR:
            Chart c1 = new Chart();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

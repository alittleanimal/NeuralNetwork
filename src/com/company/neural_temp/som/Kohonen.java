package com.company.neural_temp.som;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.learn.Training;
import com.company.neural_temp.validation.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kohonen extends Training implements Validation {

    public NeuralNet train(NeuralNet n) {

        int rows = n.getTrainSet().length;

        n = this.initNet(n);

        ArrayList<Double> listOfDistances = new ArrayList<>();

        double trainData[][] = n.getTrainSet();

        for (int epoch = 0; epoch < n.getMaxEpochs(); epoch++) {
            for (int row_i = 0; row_i < rows; row_i++) {
                listOfDistances = calcEuclideanDistance(n, trainData, row_i);
                int winnerNeuron = listOfDistances.indexOf(Collections.min(listOfDistances));
                n = fixWinnerWeights(n, winnerNeuron, row_i);
            }
        }

        return n;
    }

    private NeuralNet initNet(NeuralNet n) {

        return n;
    }

    private ArrayList<Double> calcEuclideanDistance(NeuralNet n, double[][] data, int row) {
        ArrayList<Double> listOfDistances = new ArrayList<>();

        int weight_i = 0;
        for (int cluster_i = 0; cluster_i < n.getOutputLayer().getNumberOfNeuronsInLayer(); cluster_i++) {
            double distance = 0.0;

            for (int input_j = 0; input_j < n.getInputLayer().getNumberOfNeuronsInLayer(); input_j++) {
                double weight = n.getInputLayer().getListOfNeurons().get(0).getListOfWeightOut().get(weight_i);
                distance = distance + Math.pow(data[row][input_j] - weight, 2.0);
                weight_i++;
            }

            listOfDistances.add(distance);
        }

        return listOfDistances;
    }

    private NeuralNet fixWinnerWeights(NeuralNet n, int winnerNeuron, int trainSetRow) {
        int start, last;

        start = winnerNeuron * n.getInputLayer().getNumberOfNeuronsInLayer();

        if (start < 0) {
            start = 0;
        }

        last = start + n.getInputLayer().getNumberOfNeuronsInLayer();

        List<Double> listOfOldWeights = new ArrayList<>();
        listOfOldWeights = n.getInputLayer().getListOfNeurons().get(0).getListOfWeightOut().subList(start, last);

        ArrayList<Double> listOfWeights = new ArrayList<>();
        listOfWeights = n.getInputLayer().getListOfNeurons().get(0).getListOfWeightOut();

        int col_i = 0;
        for (int j = start; j < last; j++) {
            double trainSetValue = n.getTrainSet()[trainSetRow][col_i];
            double newWeight = listOfOldWeights.get(col_i) + n.getLearningRate() * (trainSetValue - listOfOldWeights.get(col_i));

            listOfWeights.set(j, newWeight);
            col_i++;
        }

        n.getInputLayer().getListOfNeurons().get(0).setListOfWeightOut(listOfWeights);

        return n;
    }

    @Override
    public void netValidation(NeuralNet n) {

    }
}

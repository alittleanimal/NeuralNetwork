package com.company.neural_temp.learn;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.Neuron;
import com.company.neural_temp.util.Matrix;

import java.util.ArrayList;

public class LevenbergMarquardt extends Backpropagation {
    private double damping = 0.1;
    private Matrix error = null;
    private Matrix jacobian = null;

    public NeuralNet train(NeuralNet n) {
        int epoch = 0;
        setMse(1.0);

        while (getMse() > n.getTargetError()) {
            if (epoch >= n.getMaxEpochs()) break;

            int rows = n.getTrainSet().length;
            double sumErrors = 0.0;

            for (int rows_i = 0; rows_i < rows; rows_i++) {
                n = forward(n, rows_i);
                buildJacobianMatrix(n, rows_i);
                sumErrors = sumErrors + n.getErrorMean();
            }

            setMse(sumErrors / rows);
            n = updateWeights(n);
            System.out.println(getMse());
            epoch++;
        }
        return n;
    }

    private NeuralNet updateWeights(NeuralNet n) {


        return n;
    }

    private void buildJacobianMatrix(NeuralNet n, int row) {
        ArrayList<Neuron> outputLayer = new ArrayList<>();
        outputLayer = n.getOutputLayer().getListOfNeurons();

        ArrayList<Neuron> hiddenLayer = new ArrayList<>();
        hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();

        NeuralNet nb = backpropagation(n, row);

        int numberOfInputs = n.getInputLayer().getNumberOfNeuronsInLayer();
        int numberOfHiddenNeurons = n.getHiddenLayer().getNumberOfNeuronsInLayer();
        int numberOfOutputs = n.getOutputLayer().getNumberOfNeuronsInLayer();

        if (jacobian == null) {
            jacobian = new Matrix(n.getTrainSet().length,
                    (numberOfInputs) * (numberOfHiddenNeurons - 1) + (numberOfHiddenNeurons) * (numberOfOutputs));
        }

        int i = 0;
        //Hidden Layer
        for (Neuron neuron : hiddenLayer) {
            ArrayList<Double> hiddenLayerInputWeights = new ArrayList<>();
            hiddenLayerInputWeights = neuron.getListOfWeightIn();

            if (hiddenLayerInputWeights.size() > 0) {
                for (int j = 0; j < n.getInputLayer().getNumberOfNeuronsInLayer(); j++) {
                    jacobian.setValue(row, ((i - 1) * (numberOfInputs)) + (j),
                            (neuron.getSensibility() * n.getTrainSet()[row][j] / n.getErrorMean()));
                }
            } else {
                jacobian.setValue(row, i * numberOfInputs, 1.0);
            }

            i++;
        }

        if (error == null) {
            error = new Matrix(n.getTrainSet().length, 1);
        }

        i = 0;

        //Output Layer
        for (Neuron output : outputLayer) {
            int j = 0;
            for (Neuron neuron : hiddenLayer) {
                jacobian.setValue(row, numberOfOutputs * (numberOfHiddenNeurons - 1) + (i * (numberOfHiddenNeurons)) + j,
                        (output.getSensibility() * neuron.getOutputValue() / n.getErrorMean()));
                j++;
            }
            i++;
        }

        error.setValue(row, 0, n.getErrorMean());
    }
}

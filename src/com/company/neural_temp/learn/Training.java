package com.company.neural_temp.learn;

import com.company.neural_temp.InputLayer;
import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.Neuron;

import java.util.ArrayList;

public abstract class Training {
    private int epochs;
    private double error;
    private double mse;

    public enum TrainingTypesENUM {
        PERCEPTRON, ADALINE, BACKPROPAGATION, LEVENBERG_MARQUARDT, KOHONEN, BACKPROPAGATION_ONLINE, ART;
    }

    public enum ActivationFncEnum {
        STEP, LINEAR, SIGLOG, HYPERTAN
    }

    public NeuralNet train(NeuralNet n) {
        ArrayList<Double> inputWeightIn = new ArrayList<>();

        int rows = n.getTrainSet().length;
        int cols = n.getTrainSet()[0].length;

        while (this.getEpochs() < n.getMaxEpochs()) {
            double estimatedOutput = 0.0;
            double realOutput = 0.0;

            for (int i = 0; i < rows; i++) {
                double netValue = 0.0;
                for (int j = 0; j < cols; j++) {
                    inputWeightIn = n.getInputLayer().getListOfNeurons().get(j).getListOfWeightIn();
                    double inputWeight = inputWeightIn.get(0);
                    netValue = netValue + inputWeight * n.getTrainSet()[i][j];
                }

                estimatedOutput = this.activationFnc(n.getActivationFnc(), netValue);
                realOutput = n.getRealOutputSet()[i];

                this.setError(realOutput - estimatedOutput);

                if (Math.abs(this.getError()) > n.getTargetError()) {
                    InputLayer inputLayer = new InputLayer();
                    inputLayer.setListOfNeurons(this.teachNeuronsOfLayer(cols, i, n, netValue));
                    n.setInputLayer(inputLayer);
                }
            }

            this.setMse(Math.pow(realOutput - estimatedOutput, 2.0));
            n.getListOfMSE().add(this.getMse());

            this.setEpochs(this.getEpochs() + 1);
        }

        n.setTrainingError(this.getError());
        return n;
    }

    public ArrayList<Neuron> teachNeuronsOfLayer(int numberOfInputNeurons, int line, NeuralNet n, double netValue) {
        ArrayList<Neuron> listOfNeurons = new ArrayList<>();
        ArrayList<Double> inputWeightsInNew = new ArrayList<>();
        ArrayList<Double> inputWeightsInOld = new ArrayList<>();

        for (int j = 0; j < numberOfInputNeurons; j++) {
            inputWeightsInOld = n.getInputLayer().getListOfNeurons().get(j).getListOfWeightIn();
            double inputWeightOld = inputWeightsInOld.get(0);

            inputWeightsInNew.add(this.calcNewWeight(n.getTrainType(), inputWeightOld, n, this.getError(), n.getTrainSet()[line][j], netValue));

            Neuron neuron = new Neuron();
            neuron.setListOfWeightIn(inputWeightsInNew);
            listOfNeurons.add(neuron);
            inputWeightsInNew = new ArrayList<>();
        }

        return listOfNeurons;
    }

    private double calcNewWeight(TrainingTypesENUM trainType, double inputWeightOld,
                                 NeuralNet n, double error, double trainSample, double netValue) {
        switch (trainType) {
            case ADALINE:
                return inputWeightOld + n.getLearningRate() * error * trainSample * derivativeActivationFnc(n.getActivationFnc(), netValue);
            case PERCEPTRON:
                return inputWeightOld + n.getLearningRate() * error * trainSample;
            default:
                throw new IllegalArgumentException(trainType + "does not exist in TrainingTypesENUM");
        }

    }

    public double activationFnc(ActivationFncEnum fnc, double value) {
        switch (fnc) {
            case STEP:
                return fncStep(value);
            case LINEAR:
                return fncLinear(value);
            case SIGLOG:
                return fncSigLog(value);
            case HYPERTAN:
                return fncHyperTan(value);
            default:
                throw new IllegalArgumentException(fnc + " does not exist in ActivationFncENUM");
        }

    }

    public double derivativeActivationFnc(ActivationFncEnum fnc, double value) {
        switch (fnc) {
            case LINEAR:
                return derivativeFncLinear(value);
            case SIGLOG:
                return derivativeFncSigLog(value);
            case HYPERTAN:
                return derivativeFncHyperTan(value);
            default:
                throw new IllegalArgumentException(fnc + " does not exist in ActivationFncENum");
        }
    }

    private double fncStep(double v) {
        if (v >= 0) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private double fncLinear(double v) {
        return v;
    }

    private double fncSigLog(double v) {
        return 1.0 / (1.0 + Math.exp(-v));
    }

    private double fncHyperTan(double v) {
        return Math.tanh(v);
    }

    private double derivativeFncLinear(double v) {
        return 1.0;
    }

    private double derivativeFncSigLog(double v) {
        return v * (1.0 - v);
    }

    private double derivativeFncHyperTan(double v) {
        return 1.0 / Math.pow(Math.cosh(v), 2.0);
    }

    public void printTrainedNetResult(NeuralNet trainedNet) {

        int rows = trainedNet.getTrainSet().length;
        int cols = trainedNet.getTrainSet()[0].length;

        ArrayList<Double> inputWeightIn = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            double netValue = 0.0;
            for (int j = 0; j < cols; j++) {
                inputWeightIn = trainedNet.getInputLayer().getListOfNeurons()
                        .get(j).getListOfWeightIn();
                double inputWeight = inputWeightIn.get(0);
                netValue = netValue + inputWeight
                        * trainedNet.getTrainSet()[i][j];

                System.out.print(trainedNet.getTrainSet()[i][j] + "\t");
            }

            double estimatedOutput = this.activationFnc(
                    trainedNet.getActivationFnc(), netValue);

            System.out.print(" NET OUTPUT: " + estimatedOutput + "\t");
            System.out.print(" REAL OUTPUT: "
                    + trainedNet.getRealOutputSet()[i] + "\t");
            double error = estimatedOutput - trainedNet.getRealOutputSet()[i];
            System.out.print(" ERROR: " + error + "\n");

        }
    }

    public int getEpochs() {
        return epochs;
    }

    public void setEpochs(int epochs) {
        this.epochs = epochs;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getMse() {
        return mse;
    }

    public void setMse(double mse) {
        this.mse = mse;
    }
}


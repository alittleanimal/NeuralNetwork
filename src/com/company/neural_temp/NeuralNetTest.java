package com.company.neural_temp;

import com.company.neural_temp.learn.Training;

import java.util.Arrays;

public class NeuralNetTest {
    public static void main(String[] args) {
        NeuralNetTest test = new NeuralNetTest();
//        test.testPerceptron();
//        test.testAdaline();
//        test.testBackpropagation();
        test.testKohonen();
    }

    private void testPerceptron() {
        NeuralNet testNet = new NeuralNet();
        testNet = testNet.initNet(2, 0, 0, 1);
        System.out.println("------Perceptron Init Net-------");

        testNet.printNet(testNet);

        NeuralNet trainedNet = new NeuralNet();

        // first column has BIAS
        testNet.setTrainSet(new double[][]{{1.0, 0.0, 0.0}, {1.0, 0.0, 1.0}, {1.0, 1.0, 0.0}, {1.0, 1.0, 1.0}});
        testNet.setRealOutputSet(new double[]{0.0, 0.0, 0.0, 1.0});
        testNet.setMaxEpochs(10);
        testNet.setTrainingError(0.002);
        testNet.setLearningRate(1.0);
        testNet.setTrainType(Training.TrainingTypesENUM.PERCEPTRON);
        testNet.setActivationFnc(Training.ActivationFncEnum.STEP);

        trainedNet = testNet.trainNet(testNet);
        System.out.println();
        System.out.println("---------PERCEPTRON TRAINED NET---------");

        testNet.printNet(trainedNet);

        System.out.println();
        System.out.println("---------PERCEPTRON PRINT RESULT---------");

        testNet.printTrainedNetResult(trainedNet);
    }

    private void testAdaline() {
        NeuralNet testNet = new NeuralNet();

        testNet = testNet.initNet(3, 0, 0, 1);

        System.out.println("---------Adaline Init Net---------");

        testNet.printNet(testNet);

        NeuralNet trainedNet = new NeuralNet();

        // first column has BIAS
        testNet.setTrainSet(new double[][]{{1.0, 0.98, 0.94, 0.95},
                {1.0, 0.60, 0.60, 0.85}, {1.0, 0.35, 0.15, 0.15},
                {1.0, 0.25, 0.30, 0.98}, {1.0, 0.75, 0.85, 0.91},
                {1.0, 0.43, 0.57, 0.87}, {1.0, 0.05, 0.06, 0.01}});
        testNet.setRealOutputSet(new double[]{0.80, 0.59, 0.23, 0.45, 0.74,
                0.63, 0.10});
        testNet.setMaxEpochs(10);
        testNet.setTargetError(0.0001);
        testNet.setLearningRate(0.5);
        testNet.setTrainType(Training.TrainingTypesENUM.ADALINE);
        testNet.setActivationFnc(Training.ActivationFncEnum.LINEAR);

        trainedNet = testNet.trainNet(testNet);

        System.out.println();
        System.out.println("-------Adaline Trained Net--------");

        testNet.printNet(trainedNet);

        System.out.println();
        System.out.println("-------Adaline Print Result--------");

        testNet.printTrainedNetResult(trainedNet);

        System.out.println();
        System.out.println("-------Adaline Mse By Epoch--------");
        System.out.println(Arrays.deepToString(trainedNet.getListOfMSE().toArray()).replace(" ", "\n"));
    }

    private void testBackpropagation() {
        NeuralNet testNet = new NeuralNet();
        testNet = testNet.initNet(2, 1, 3, 2);

        System.out.println("---------BACKPROPAGATION INIT NET----------");
        testNet.printNet(testNet);

        NeuralNet trainedNet = new NeuralNet();

        //first column has BIAS
        testNet.setTrainSet(new double[][]{{1.0, 1.0, 0.73}, {1.0, 1.0, 0.81}, {1.0, 1.0, 0.86},
                {1.0, 1.0, 0.95}, {1.0, 0.0, 0.45}, {1.0, 1.0, 0.70},
                {1.0, 0.0, 0.51}, {1.0, 1.0, 0.89}, {1.0, 1.0, 0.79}, {1.0, 0.0, 0.54}
        });

        testNet.setRealMatrixOutputSet(new double[][] { {1.0, 0.0}, {1.0, 0.0},	{1.0, 0.0},
                {1.0, 0.0},	{1.0, 0.0},	{0.0, 1.0},
                {0.0, 1.0},	{0.0, 1.0}, {0.0, 1.0}, {0.0, 1.0}
        });

        testNet.setMaxEpochs(1000);
        testNet.setTargetError(0.002);
        testNet.setLearningRate(0.1);
        testNet.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
        testNet.setActivationFnc(Training.ActivationFncEnum.SIGLOG);
        testNet.setActivationFncOutputLayer(Training.ActivationFncEnum.LINEAR);

        trainedNet = testNet.trainNet(testNet);

        System.out.println();
        System.out.println("-----------BACKPROPAGATION TRAINED NET----------");

        testNet.printNet(trainedNet);
    }

    private void testKohonen() {
        NeuralNet testNet = new NeuralNet();

        testNet = testNet.initNet(2,0,0,2);
        NeuralNet trainedNet = new NeuralNet();

        testNet.setTrainSet(new double[][]{{ 1.0, -1.0, 1.0 }, { -1.0, -1.0, -1.0 }, { -1.0, -1.0, 1.0 },
                { 1.0, 1.0, -1.0 }, { -1.0, 1.0, 1.0   }, { 1.0, -1.0, -1.0 }});

        testNet.setValidationSet(new double[][]{{-1.0, 1.0, -1.0}, {1.0, 1.0, 1.0}});

        testNet.setMaxEpochs(10);
        testNet.setLearningRate(0.1);
        testNet.setTrainType(Training.TrainingTypesENUM.KOHONEN);

        trainedNet = testNet.trainNet(testNet);

        System.out.println();
        System.out.println("----------KOHONEN VALIDATION NET----------");

        testNet.netValidation(trainedNet);
    }
}

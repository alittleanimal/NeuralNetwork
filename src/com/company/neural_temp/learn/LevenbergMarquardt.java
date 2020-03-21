package com.company.neural_temp.learn;

import com.company.neural_temp.NeuralNet;
import com.company.neural_temp.util.Matrix;

public class LevenbergMarquardt extends Backpropagation {
    private double damping = 0.1;
    private Matrix error = null;
    private Matrix jacobian = null;

    public NeuralNet train (NeuralNet n) {


        return n;
    }

    private void buildJacobianMatrix (NeuralNet n, int row) {

    }
}

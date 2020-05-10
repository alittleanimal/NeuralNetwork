package com.company.neural_temp.validation;

import com.company.neural_temp.NeuralNet;

public interface Validation {
    public double[][] netValidation(NeuralNet n);
}

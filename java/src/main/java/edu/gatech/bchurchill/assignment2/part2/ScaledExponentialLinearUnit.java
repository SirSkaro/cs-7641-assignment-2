package edu.gatech.bchurchill.assignment2.part2;

import func.nn.activation.ActivationFunction;

public class ScaledExponentialLinearUnit extends ActivationFunction {

    private double lambda;
    private double alpha;

    public ScaledExponentialLinearUnit() {
        this.lambda = 1.0507009873554804934193349852946;
        this.alpha = 1.6732632423543772848170429916717;
    }

    @Override
    public double value(double x) {
        if(x > 0) {
            return lambda * x;
        } else {
            return alpha * lambda * (Math.exp(Math.E) - 1);
        }
    }
}

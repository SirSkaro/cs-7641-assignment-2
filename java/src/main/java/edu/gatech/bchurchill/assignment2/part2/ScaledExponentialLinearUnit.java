package edu.gatech.bchurchill.assignment2.part2;

import func.nn.activation.DifferentiableActivationFunction;

public class ScaledExponentialLinearUnit extends DifferentiableActivationFunction {

    private double scale;
    private double alpha;

    public ScaledExponentialLinearUnit() {
        this.scale = 1.0507009873554804934193349852946;
        this.alpha = 1.6732632423543772848170429916717;
    }

    @Override
    public double derivative(double x) {
        if(x > 0) {
            return scale;
        } else {
            return alpha * scale * Math.exp(x);
        }
    }

    @Override
    public double value(double x) {
        if(x > 0) {
            return scale * x;
        } else {
            return alpha * scale * (Math.exp(x) - 1);
        }
    }
}

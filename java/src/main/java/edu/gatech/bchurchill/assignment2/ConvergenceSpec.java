package edu.gatech.bchurchill.assignment2;

public class ConvergenceSpec {
    public int iterations = 50_000;
    public int convergenceThreshold = 20;
    public double convergenceTolerance = 0.125;

    public ConvergenceSpec(int iterations, int convergenceThreshold, double convergenceTolerance) {
        this.iterations = iterations;
        this.convergenceThreshold = convergenceThreshold;
        this.convergenceTolerance = convergenceTolerance;
    }

}

package edu.gatech.bchurchill.assignment2;

import shared.Trainer;

public class ConvergenceIterationTrainer implements Trainer {

    private Trainer trainer;
    private int threshold;
    private int iterations;
    private int maxIterations;

    public ConvergenceIterationTrainer(Trainer trainer, int threshold, int maxIterations) {
        this.trainer = trainer;
        this.threshold = threshold;
        this.maxIterations = maxIterations;
    }

    @Override
    public double train() {
        double error = Double.MAX_VALUE;
        int duplicateErrorCount = 0;

        double lastError;
        do {
            ++this.iterations;
            lastError = error;
            error = this.trainer.train();

            if(lastError == error) {
                duplicateErrorCount++;
            } else {
                duplicateErrorCount = 0;
            }

        } while(duplicateErrorCount < this.threshold
                && this.iterations < this.maxIterations);

        return error;
    }

    public int getIterations() {
        return this.iterations;
    }

}

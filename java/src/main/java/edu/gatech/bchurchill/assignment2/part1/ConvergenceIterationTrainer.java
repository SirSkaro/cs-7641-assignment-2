package edu.gatech.bchurchill.assignment2.part1;

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
        double score = Double.MAX_VALUE;
        int duplicateScoreCount = 0;
        double previousScore;

        do {
            ++this.iterations;
            previousScore = score;
            score = this.trainer.train();

            if(previousScore == score) {
                duplicateScoreCount++;
            } else {
                duplicateScoreCount = 0;
            }

        } while(duplicateScoreCount < this.threshold
                && this.iterations < this.maxIterations);

        return score;
    }

    public int getIterations() {
        return this.iterations;
    }

}

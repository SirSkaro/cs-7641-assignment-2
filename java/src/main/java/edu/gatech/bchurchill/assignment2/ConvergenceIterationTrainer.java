package edu.gatech.bchurchill.assignment2;

import shared.Trainer;

public class ConvergenceIterationTrainer implements Trainer {

    private Trainer trainer;
    private int threshold;
    private int iterations;
    private int maxIterations;
    private double tolerance;

    public ConvergenceIterationTrainer(Trainer trainer, int threshold, int maxIterations, double tolerance) {
        this.trainer = trainer;
        this.threshold = threshold;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
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

            if(equalWithinTolerance(score, previousScore)) {
                duplicateScoreCount++;
            } else {
                duplicateScoreCount = 0;
            }


                System.out.println(String.format("Iteration %d | score: %.10f", iterations, score));


        } while(stoppingConditionNotMet(duplicateScoreCount));

        return score;
    }

    public int getIterations() {
        return this.iterations;
    }

    private boolean equalWithinTolerance(double score1, double score2) {
        return Math.abs(score1 - score2) <= tolerance;
    }

    private boolean stoppingConditionNotMet(int duplicateScoreCount) {
        boolean lessThanThreshold = duplicateScoreCount < this.threshold;
        boolean underMaxIterations = (this.maxIterations == -1) ? true : (this.iterations < this.maxIterations);

        return lessThanThreshold && underMaxIterations;
    }

}

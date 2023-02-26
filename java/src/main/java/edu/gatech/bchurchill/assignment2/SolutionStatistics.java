package edu.gatech.bchurchill.assignment2;

import shared.Instance;

public class SolutionStatistics {
    public Instance finalPoint;
    public long trainTimeInMs;
    public double score;
    public int iterations;

    public SolutionStatistics(Instance finalPoint, long trainTime, int iterations, double score) {
        this.finalPoint = finalPoint;
        this.trainTimeInMs = trainTime;
        this.iterations = iterations;
        this.score = score;
    }

    @Override
    public String toString() {
        return "{" +
                //"finalPoint=" + (finalPoint != null ? finalPoint.getData().toString() : "N/A") +
                ", train time (ms)=" + trainTimeInMs +
                ", iterations=" + iterations +
                ", score=" + (score == Double.MAX_VALUE ? "inf" : score)  +
                '}';
    }
}

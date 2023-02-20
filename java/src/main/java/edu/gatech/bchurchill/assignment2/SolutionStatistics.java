package edu.gatech.bchurchill.assignment2;

import shared.Instance;

public class SolutionStatistics {
    public Instance finalPoint;
    public long trainTimeInMs;
    public double score;

    public SolutionStatistics(Instance finalPoint, long trainTime, double score) {
        this.finalPoint = finalPoint;
        this.trainTimeInMs = trainTime;
        this.score = score;
    }

    @Override
    public String toString() {
        return "{" +
                "finalPoint=" + finalPoint.getData() +
                ", train time (ms)=" + trainTimeInMs +
                ", score=" + score +
                '}';
    }
}
